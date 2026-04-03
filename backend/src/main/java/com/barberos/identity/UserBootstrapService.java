package com.barberos.identity;

import com.barberos.identity.internal.ProfileRepository;
import com.barberos.identity.internal.SlugGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
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
 * D23: Creates Profile eagerly in the same transaction as User.
 */
@Service
public class UserBootstrapService {

    private static final Logger log = LoggerFactory.getLogger(UserBootstrapService.class);

    private final UserRepository userRepository;
    private final UserOAuth2IdentityRepository identityRepository;
    private final ProfileRepository profileRepository;
    private final SlugGenerator slugGenerator;
    private final TransactionTemplate transactionTemplate;
    private final TransactionTemplate isolatedTxTemplate;

    public UserBootstrapService(UserRepository userRepository,
                                UserOAuth2IdentityRepository identityRepository,
                                ProfileRepository profileRepository,
                                SlugGenerator slugGenerator,
                                TransactionTemplate transactionTemplate,
                                PlatformTransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.identityRepository = identityRepository;
        this.profileRepository = profileRepository;
        this.slugGenerator = slugGenerator;
        this.transactionTemplate = transactionTemplate;
        // R3 fix: REQUIRES_NEW for user creation — MagicLinkService.verifyToken wraps
        // provisionMagicLink in its own transaction. Default REQUIRED would join it,
        // and a DIV on email collision would poison the outer tx, breaking recovery.
        this.isolatedTxTemplate = new TransactionTemplate(transactionManager);
        this.isolatedTxTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
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
        // D32: If ghost user exists, claim it and link OAuth2 identity
        var existingUser = userRepository.findByEmail(normalizedEmail);
        if (existingUser.isPresent()) {
            UserEntity user = existingUser.get();
            if (user.isDeleted()) {
                // Deleted user — proceed with new account creation
            } else if (!user.isClaimed()) {
                // Ghost user — claim and link OAuth2 identity
                return claimGhostUserWithOAuth2(user, registrationId, subject, normalizedEmail);
            } else {
                log.info("OAuth2 login email collision: email={} provider={} — user must link explicitly",
                        normalizedEmail, registrationId);
                throw new OAuth2AuthenticationException(
                        new OAuth2Error("email_conflict",
                                "An account with this email already exists. Sign in with your original method to link accounts.", null));
            }
        }

        // New user — create user + identity in same transaction
        try {
            return createOAuth2UserInTransaction(normalizedEmail, registrationId, subject, displayName);
        } catch (DataIntegrityViolationException e) {
            // Race condition — identity or email was created concurrently
            return identityRepository.findByProviderAndSubject(registrationId, subject)
                    .map(identity -> userRepository.findById(identity.getUserId()).orElseThrow())
                    .orElseGet(() -> {
                        // R2 fix: re-check email — may be an unclaimed ghost created concurrently
                        var raceWinner = userRepository.findByEmail(normalizedEmail);
                        if (raceWinner.isPresent() && !raceWinner.get().isDeleted()
                                && !raceWinner.get().isClaimed()) {
                            return claimGhostUserWithOAuth2(
                                    raceWinner.get(), registrationId, subject, normalizedEmail);
                        }
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

    /**
     * Provision or resolve a user for magic link login.
     * D32: If an unclaimed ghost user exists, claim it.
     * C47: Skip deleted users.
     */
    public UserEntity provisionMagicLink(String email) {
        String normalizedEmail = normalizeEmail(email);
        return userRepository.findByEmail(normalizedEmail)
                .map(user -> {
                    if (user.isDeleted()) {
                        // Deleted user — email was anonymized, so this shouldn't match.
                        // But if somehow it does, treat as new registration.
                        return createMagicLinkUserInTransaction(normalizedEmail);
                    }
                    if (!user.isClaimed()) {
                        // Ghost user — claim it (D32)
                        return claimGhostUser(user);
                    }
                    return user;
                })
                .orElseGet(() -> {
                    try {
                        return createMagicLinkUserInTransaction(normalizedEmail);
                    } catch (DataIntegrityViolationException e) {
                        // R2 fix: race recovery must apply the same claimed/deleted checks
                        UserEntity raceWinner = userRepository.findByEmail(normalizedEmail).orElseThrow();
                        if (raceWinner.isDeleted()) {
                            throw new IllegalStateException("Account has been deleted");
                        }
                        if (!raceWinner.isClaimed()) {
                            return claimGhostUser(raceWinner);
                        }
                        return raceWinner;
                    }
                });
    }

    /**
     * D32: Claim a ghost user during OAuth2 login and link the identity.
     * R1-P0 fix: re-load inside transaction (was detached).
     * R2 fix: pessimistic lock to prevent concurrent dual-provider claim race (D18).
     * R2 fix: re-check isDeleted/isClaimed after lock acquisition.
     * R2 fix: explicit profileRepository.saveAndFlush for claimed flag.
     */
    private UserEntity claimGhostUserWithOAuth2(UserEntity ghost, String registrationId,
                                                 String subject, String email) {
        return transactionTemplate.execute(status -> {
            UserEntity managedGhost = userRepository.findByIdForUpdate(ghost.getId()).orElseThrow();
            if (managedGhost.isDeleted()) {
                throw new OAuth2AuthenticationException(
                        new OAuth2Error("user_not_found", "User not found", null));
            }
            if (managedGhost.isClaimed()) {
                throw new OAuth2AuthenticationException(
                        new OAuth2Error("email_conflict",
                                "An account with this email already exists. Sign in with your original method to link accounts.", null));
            }
            managedGhost.setClaimed(true);
            userRepository.saveAndFlush(managedGhost);
            profileRepository.findByUserId(managedGhost.getId()).ifPresent(profile -> {
                profile.setClaimed(true);
                profileRepository.saveAndFlush(profile);
            });
            var identity = new UserOAuth2IdentityEntity(managedGhost.getId(), registrationId, subject, email);
            identityRepository.saveAndFlush(identity);
            log.info("Ghost user claimed via OAuth2: userId={} provider={}", managedGhost.getId(), registrationId);
            return managedGhost;
        });
    }

    /**
     * D32: Claim a ghost user during magic link login.
     * R2 fix: pessimistic lock + re-check deleted/claimed state + explicit profile save.
     * Idempotent: if already claimed by concurrent login, returns the claimed user.
     */
    private UserEntity claimGhostUser(UserEntity ghost) {
        return transactionTemplate.execute(status -> {
            UserEntity managedGhost = userRepository.findByIdForUpdate(ghost.getId()).orElseThrow();
            if (managedGhost.isDeleted()) {
                throw new IllegalStateException("User has been deleted");
            }
            if (managedGhost.isClaimed()) {
                return managedGhost;
            }
            managedGhost.setClaimed(true);
            userRepository.saveAndFlush(managedGhost);
            profileRepository.findByUserId(managedGhost.getId()).ifPresent(profile -> {
                profile.setClaimed(true);
                profileRepository.saveAndFlush(profile);
            });
            log.info("Ghost user claimed via magic link: userId={}", managedGhost.getId());
            return managedGhost;
        });
    }

    private UserEntity createOAuth2UserInTransaction(String email, String registrationId,
                                                     String subject, String displayName) {
        return transactionTemplate.execute(status -> {
            var user = new UserEntity(email, displayName);
            userRepository.saveAndFlush(user);
            var identity = new UserOAuth2IdentityEntity(user.getId(), registrationId, subject, email);
            identityRepository.saveAndFlush(identity);
            createProfileForUser(user);
            log.info("New user created via OAuth2: email={} provider={}", email, registrationId);
            return user;
        });
    }

    private UserEntity createMagicLinkUserInTransaction(String email) {
        return isolatedTxTemplate.execute(status -> {
            String name = email.split("@")[0];
            var user = new UserEntity(email, name);
            userRepository.saveAndFlush(user);
            createProfileForUser(user);
            log.info("New user created via magic link: email={}", email);
            return user;
        });
    }

    /**
     * D23: Eager profile creation in the same transaction as user.
     * R4 ACCEPTED_RISK: Slug TOCTOU retry removed — PostgreSQL aborts the transaction
     * after a constraint violation, making in-tx retry impossible without savepoints.
     * SlugGenerator's 4-char random suffix (36^4 = 1.7M namespace) makes collision
     * probability negligible. If it occurs, the transaction fails and user retries.
     */
    private void createProfileForUser(UserEntity user) {
        var profile = new ProfileEntity(user.getId(), user.getName(), slugGenerator.generate(user.getName()));
        profileRepository.saveAndFlush(profile);
    }

    /** D20: NFKC normalization + lowercase */
    public static String normalizeEmail(String email) {
        if (email == null) return null;
        return Normalizer.normalize(email, Normalizer.Form.NFKC).toLowerCase(Locale.ROOT).trim();
    }
}
