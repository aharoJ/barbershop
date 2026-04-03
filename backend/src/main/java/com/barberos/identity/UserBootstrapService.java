package com.barberos.identity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Public API for user provisioning. Called by auth module during OAuth2
 * login and magic link verification.
 *
 * D16: Uses user_oauth2_identities join table (not columns on users).
 * D18: Prompt-based linking — does NOT auto-link on email collision.
 * D20: NFKC email normalization.
 */
@Service
public class UserBootstrapService {

    private static final Logger log = LoggerFactory.getLogger(UserBootstrapService.class);

    private final UserRepository userRepository;
    private final UserOAuth2IdentityRepository identityRepository;
    private final TransactionTemplate transactionTemplate;

    public UserBootstrapService(UserRepository userRepository,
                                UserOAuth2IdentityRepository identityRepository,
                                TransactionTemplate transactionTemplate) {
        this.userRepository = userRepository;
        this.identityRepository = identityRepository;
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * Provision or resolve a user for OAuth2 login.
     * D18: If email collision with existing user, throw — do NOT auto-link.
     * User must explicitly link via confirmLinkIdentity().
     */
    public UserEntity provisionOAuth2(String email, String registrationId,
                                      String subject, String displayName) {
        String normalizedEmail = normalizeEmail(email);

        // Check if this exact identity already exists
        var existingIdentity = identityRepository.findByProviderAndSubject(registrationId, subject);
        if (existingIdentity.isPresent()) {
            return userRepository.findById(existingIdentity.get().getUserId())
                    .orElseThrow(() -> new OAuth2AuthenticationException(
                            new OAuth2Error("user_not_found", "User not found", null)));
        }

        // Check email collision — D18: prompt, don't auto-link
        var existingUser = userRepository.findByEmail(normalizedEmail);
        if (existingUser.isPresent()) {
            log.info("OAuth2 login email collision: email={} provider={} — user must link explicitly",
                    normalizedEmail, registrationId);
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("email_conflict",
                            "An account with this email already exists. Sign in with your original method to link accounts.", null));
        }

        // New user — create user + identity in same transaction
        try {
            return createOAuth2UserInTransaction(normalizedEmail, registrationId, subject, displayName);
        } catch (DataIntegrityViolationException e) {
            // Race condition — identity or email was created concurrently
            return identityRepository.findByProviderAndSubject(registrationId, subject)
                    .map(identity -> userRepository.findById(identity.getUserId()).orElseThrow())
                    .orElseGet(() -> {
                        // Email collision from concurrent magic link creation
                        throw new OAuth2AuthenticationException(
                                new OAuth2Error("email_conflict",
                                        "An account with this email already exists. Sign in with your original method to link accounts.", null));
                    });
        }
    }

    /**
     * Explicitly link an OAuth2 identity to an existing authenticated user.
     * D18: Requires the user to be already authenticated (proving ownership).
     * D22: Never unlinks the last auth method (magic link users always keep magic link).
     */
    public void confirmLinkIdentity(UserEntity authenticatedUser, String registrationId,
                                     String subject, String email) {
        String normalizedEmail = normalizeEmail(email);
        transactionTemplate.execute(status -> {
            // Verify identity isn't already linked to another user
            var existing = identityRepository.findByProviderAndSubject(registrationId, subject);
            if (existing.isPresent()) {
                throw new IllegalStateException("This OAuth2 identity is already linked to an account");
            }
            var identity = new UserOAuth2IdentityEntity(
                    authenticatedUser.getId(), registrationId, subject, normalizedEmail);
            identityRepository.saveAndFlush(identity);
            log.info("Linked OAuth2 identity: userId={} provider={}", authenticatedUser.getId(), registrationId);
            return null;
        });
    }

    public UserEntity provisionMagicLink(String email) {
        String normalizedEmail = normalizeEmail(email);
        return userRepository.findByEmail(normalizedEmail)
                .orElseGet(() -> {
                    try {
                        return createMagicLinkUserInTransaction(normalizedEmail);
                    } catch (DataIntegrityViolationException e) {
                        return userRepository.findByEmail(normalizedEmail).orElseThrow();
                    }
                });
    }

    private UserEntity createOAuth2UserInTransaction(String email, String registrationId,
                                                     String subject, String displayName) {
        return transactionTemplate.execute(status -> {
            var user = new UserEntity(email, displayName);
            userRepository.saveAndFlush(user);
            var identity = new UserOAuth2IdentityEntity(user.getId(), registrationId, subject, email);
            identityRepository.saveAndFlush(identity);
            log.info("New user created via OAuth2: email={} provider={}", email, registrationId);
            return user;
        });
    }

    private UserEntity createMagicLinkUserInTransaction(String email) {
        return transactionTemplate.execute(status -> {
            String name = email.split("@")[0];
            var user = new UserEntity(email, name);
            userRepository.saveAndFlush(user);
            log.info("New user created via magic link: email={}", email);
            return user;
        });
    }

    /** D20: NFKC normalization + lowercase */
    public static String normalizeEmail(String email) {
        if (email == null) return null;
        return Normalizer.normalize(email, Normalizer.Form.NFKC).toLowerCase(Locale.ROOT).trim();
    }
}
