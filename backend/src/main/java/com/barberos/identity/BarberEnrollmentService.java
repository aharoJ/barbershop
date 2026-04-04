package com.barberos.identity;

import com.barberos.identity.internal.BarberDetailsRepository;
import com.barberos.identity.internal.PlatformSpecialties;
import com.barberos.identity.internal.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * D24: Explicit barber onboarding — creates BarberDetails for an enrolled user.
 * Public API: consumed by ProfileController and potentially Shop module.
 */
@Service
public class BarberEnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(BarberEnrollmentService.class);

    private final ProfileService profileService;
    private final BarberDetailsRepository barberDetailsRepository;

    public BarberEnrollmentService(ProfileService profileService,
                                    BarberDetailsRepository barberDetailsRepository) {
        this.profileService = profileService;
        this.barberDetailsRepository = barberDetailsRepository;
    }

    /**
     * Resolve barber's profile ID from user ID. Used by Shop module for association lookups.
     * Returns the profile ID which equals barber_details.profile_id (D33).
     */
    @Transactional(readOnly = true)
    public UUID resolveBarberProfileId(UUID userId) {
        ProfileEntity profile = profileService.getByUserId(userId);
        if (!barberDetailsRepository.existsByProfileId(profile.getId())) {
            throw new IllegalStateException("User is not enrolled as a barber");
        }
        return profile.getId();
    }

    @Transactional
    public BarberDetailsEntity enrollAsBarber(UUID userId, String[] specialties,
                                               Integer experienceYears, String licenseNumber) {
        ProfileEntity profile = profileService.getByUserId(userId);

        if (barberDetailsRepository.existsByProfileId(profile.getId())) {
            throw new IllegalStateException("User is already enrolled as a barber");
        }

        PlatformSpecialties.validate(specialties);

        var details = new BarberDetailsEntity(profile);
        details.setSpecialties(specialties != null ? specialties : new String[]{});
        details.setExperienceYears(experienceYears);
        details.setLicenseNumber(licenseNumber);
        details.setAcceptingNewClients(true);
        details.setVisibility(BarberVisibility.DRAFT);

        try {
            details = barberDetailsRepository.saveAndFlush(details);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("User is already enrolled as a barber");
        }
        log.info("User enrolled as barber: userId={} profileId={}", userId, profile.getId());
        return details;
    }
}
