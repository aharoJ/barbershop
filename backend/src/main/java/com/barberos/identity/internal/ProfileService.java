package com.barberos.identity.internal;

import com.barberos.identity.BarberDetailsEntity;
import com.barberos.identity.BarberVisibility;
import com.barberos.identity.ProfileEntity;
import com.barberos.shared.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final BarberDetailsRepository barberDetailsRepository;

    public ProfileService(ProfileRepository profileRepository,
                          BarberDetailsRepository barberDetailsRepository) {
        this.profileRepository = profileRepository;
        this.barberDetailsRepository = barberDetailsRepository;
    }

    @Transactional(readOnly = true)
    public ProfileEntity getByUserId(UUID userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    @Transactional(readOnly = true)
    public ProfileEntity getBySlug(String slug) {
        return profileRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    @Transactional(readOnly = true)
    public BarberDetailsEntity getBarberDetails(UUID profileId) {
        return barberDetailsRepository.findById(profileId).orElse(null);
    }

    /** Public barber profile: must be LISTED and have BarberDetails. */
    @Transactional(readOnly = true)
    public PublicBarberProfile getPublicBarberBySlug(String slug) {
        ProfileEntity profile = profileRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));

        BarberDetailsEntity details = barberDetailsRepository.findById(profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Barber not found"));

        if (details.getVisibility() != BarberVisibility.LISTED) {
            throw new ResourceNotFoundException("Barber not found");
        }

        return new PublicBarberProfile(profile, details);
    }

    /**
     * PATCH semantics: null = no change, empty string = clear, non-empty = update.
     * displayName cannot be cleared (blank check in code, not @NotBlank annotation).
     */
    @Transactional
    public ProfileEntity updateProfile(UUID userId, String displayName, String bio,
                                        String phoneNumber, String timezone) {
        ProfileEntity profile = getByUserId(userId);
        if (displayName != null && !displayName.isBlank()) {
            profile.setDisplayName(displayName);
        }
        if (bio != null) profile.setBio(bio.isEmpty() ? null : bio);
        if (phoneNumber != null) profile.setPhoneNumber(phoneNumber.isEmpty() ? null : phoneNumber);
        if (timezone != null) profile.setTimezone(timezone.isEmpty() ? null : timezone);
        return profile;
    }

    public record PublicBarberProfile(ProfileEntity profile, BarberDetailsEntity barberDetails) {
    }
}
