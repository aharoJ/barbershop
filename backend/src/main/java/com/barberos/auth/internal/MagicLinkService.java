package com.barberos.auth.internal;

import com.barberos.identity.UserBootstrapService;
import com.barberos.identity.UserEntity;
import com.barberos.shared.TooManyRequestsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MagicLinkService {

    private static final Logger log = LoggerFactory.getLogger(MagicLinkService.class);
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String BLOCKED_EMAIL_DOMAIN = "@github.auth.barberos.com";
    private static final int MAX_VERIFY_ATTEMPTS_PER_HOUR = 10;
    private static final int VERIFY_MAP_MAX_SIZE = 10_000;
    private static final String NULL_IP_SENTINEL = "__null_ip__";

    private final MagicLinkTokenRepository tokenRepository;
    private final UserBootstrapService userBootstrapService;
    private final MagicLinkEmailSender emailSender;
    private final TransactionTemplate transactionTemplate;

    private final ConcurrentHashMap<String, VerifyWindow> verifyAttempts = new ConcurrentHashMap<>();

    @Value("${app.magic-link.token-expiry-minutes:15}")
    private int tokenExpiryMinutes;

    @Value("${app.magic-link.max-requests-per-hour:3}")
    private int maxRequestsPerHour;

    @Value("${app.magic-link.base-url:http://localhost:3000}")
    private String baseUrl;

    public MagicLinkService(MagicLinkTokenRepository tokenRepository,
                            UserBootstrapService userBootstrapService,
                            MagicLinkEmailSender emailSender,
                            TransactionTemplate transactionTemplate) {
        this.tokenRepository = tokenRepository;
        this.userBootstrapService = userBootstrapService;
        this.emailSender = emailSender;
        this.transactionTemplate = transactionTemplate;
    }

    public void sendMagicLink(String email) {
        String normalizedEmail = UserBootstrapService.normalizeEmail(email);

        if (normalizedEmail.contains(BLOCKED_EMAIL_DOMAIN)) {
            log.warn("Blocked magic link for synthetic email domain");
            return;
        }

        Instant oneHourAgo = Instant.now().minus(1, ChronoUnit.HOURS);
        long recentRequests = tokenRepository.countByEmailAndCreatedAtAfter(normalizedEmail, oneHourAgo);

        if (recentRequests >= maxRequestsPerHour) {
            log.warn("Magic link rate limit exceeded for email={}", normalizedEmail);
            return;
        }

        // D17: Invalidate old tokens + create new token in SINGLE transaction
        String rawToken = generateToken();
        String tokenHash = sha256(rawToken);
        Instant expiresAt = Instant.now().plus(tokenExpiryMinutes, ChronoUnit.MINUTES);

        transactionTemplate.execute(status -> {
            tokenRepository.invalidateTokensForEmail(normalizedEmail);
            tokenRepository.save(new MagicLinkTokenEntity(tokenHash, normalizedEmail, expiresAt));
            return null;
        });

        String verifyUrl = baseUrl + "/login/magic-link/verify?token=" + rawToken;
        emailSender.send(normalizedEmail, verifyUrl);

        log.info("Magic link sent for email={}", normalizedEmail);
    }

    public UserEntity verifyToken(String rawToken, String clientIp) {
        if (!checkVerifyRateLimit(clientIp)) {
            throw new TooManyRequestsException("Too many verification attempts. Try again later.");
        }

        String tokenHash = sha256(rawToken);

        return transactionTemplate.execute(status -> {
            int consumed = tokenRepository.consumeToken(tokenHash);
            if (consumed == 0) {
                throw new IllegalArgumentException("Invalid, expired, or already used magic link");
            }

            MagicLinkTokenEntity token = tokenRepository.findByTokenHash(tokenHash)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid magic link"));

            UserEntity user = userBootstrapService.provisionMagicLink(token.getEmail());

            log.info("Magic link verified for email={}", token.getEmail());

            return user;
        });
    }

    public void establishSession(UserEntity user, HttpServletRequest request) {
        var authentication = new UsernamePasswordAuthenticationToken(user.getId(), null, List.of());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession newSession = request.getSession(true);
        newSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
    }

    public String getDashboardUrl() {
        return baseUrl + "/dashboard";
    }

    private boolean checkVerifyRateLimit(String ip) {
        // Fix: null IP uses sentinel key instead of bypassing
        String key = (ip != null) ? ip : NULL_IP_SENTINEL;

        // Evict stale entries when map grows too large
        if (verifyAttempts.size() > VERIFY_MAP_MAX_SIZE) {
            verifyAttempts.entrySet().removeIf(entry -> entry.getValue().isExpired());
        }

        var window = verifyAttempts.compute(key, (k, existing) -> {
            if (existing == null || existing.isExpired()) {
                return new VerifyWindow();
            }
            return existing;
        });
        return window.increment() <= MAX_VERIFY_ATTEMPTS_PER_HOUR;
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    private static class VerifyWindow {
        private final Instant start = Instant.now();
        private final AtomicInteger count = new AtomicInteger(0);

        boolean isExpired() {
            return Instant.now().isAfter(start.plus(1, ChronoUnit.HOURS));
        }

        int increment() {
            return count.incrementAndGet();
        }
    }
}
