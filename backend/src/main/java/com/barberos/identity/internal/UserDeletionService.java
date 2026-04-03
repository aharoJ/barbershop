package com.barberos.identity.internal;

import com.barberos.identity.BarberVisibility;
import com.barberos.identity.UserEntity;
import com.barberos.identity.UserOAuth2IdentityRepository;
import com.barberos.identity.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * D29: Soft delete + PII anonymization.
 * Hard-deletes auth records, anonymizes PII, preserves business data.
 */
@Service
public class UserDeletionService {

    private static final Logger log = LoggerFactory.getLogger(UserDeletionService.class);

    private final UserRepository userRepository;
    private final UserOAuth2IdentityRepository identityRepository;
    private final ProfileRepository profileRepository;
    private final BarberDetailsRepository barberDetailsRepository;

    public UserDeletionService(UserRepository userRepository,
                                UserOAuth2IdentityRepository identityRepository,
                                ProfileRepository profileRepository,
                                BarberDetailsRepository barberDetailsRepository) {
        this.userRepository = userRepository;
        this.identityRepository = identityRepository;
        this.profileRepository = profileRepository;
        this.barberDetailsRepository = barberDetailsRepository;
    }

    @Transactional
    public void deleteUser(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.isDeleted()) {
            throw new IllegalStateException("User is already deleted");
        }

        // Hard delete auth records — prevents re-login
        identityRepository.deleteAll(identityRepository.findByUserId(userId));

        // Anonymize user PII
        user.setEmail("deleted-" + userId + "@deleted.local");
        user.setName("Deleted User");
        user.setDeletedAt(Instant.now());
        user.setDeleted(true);

        // Anonymize profile (including slug — PII-adjacent, frees namespace)
        profileRepository.findByUserId(userId).ifPresent(profile -> {
            profile.setDisplayName("Deleted User");
            profile.setBio(null);
            profile.setPhoneNumber(null);
            profile.setPhotoUrl(null);
            profile.setTimezone(null);
            profile.setSlug("deleted-" + profile.getId());

            // Deactivate barber details if enrolled
            barberDetailsRepository.findById(profile.getId()).ifPresent(details -> {
                details.setLicenseNumber(null);
                details.setAcceptingNewClients(false);
                details.setVisibility(BarberVisibility.DRAFT);
            });
        });

        log.info("User deleted (anonymized): userId={}", userId);
    }
}
