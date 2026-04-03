package com.barberos.identity;

import com.barberos.identity.internal.BarberDetailsRepository;
import com.barberos.identity.internal.ProfileRepository;
import com.barberos.identity.internal.SlugGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.barberos.identity.UserBootstrapService.normalizeEmail;

/**
 * D32: Ghost/proxy profile lifecycle.
 * Shop owners create barber profiles on behalf of tech-averse barbers.
 * Ghost users are real User records with isClaimed=false and no OAuth2 identities.
 */
@Service
public class GhostService {

    private static final Logger log = LoggerFactory.getLogger(GhostService.class);

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final BarberDetailsRepository barberDetailsRepository;
    private final SlugGenerator slugGenerator;

    public GhostService(UserRepository userRepository,
                         ProfileRepository profileRepository,
                         BarberDetailsRepository barberDetailsRepository,
                         SlugGenerator slugGenerator) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.barberDetailsRepository = barberDetailsRepository;
        this.slugGenerator = slugGenerator;
    }

    /**
     * Create an unclaimed ghost barber on behalf of a shop owner.
     * C45: Prevent ghost creation for already-claimed emails.
     */
    @Transactional
    public ProfileEntity createGhostBarber(UUID ownerUserId, String email, String name) {
        String normalizedEmail = normalizeEmail(email);

        if (userRepository.existsByEmailAndClaimedTrue(normalizedEmail)) {
            throw new IllegalArgumentException("A user with this email already exists");
        }

        // Also check for existing unclaimed ghost with same email
        if (userRepository.findByEmailAndClaimedFalse(normalizedEmail).isPresent()) {
            throw new IllegalArgumentException("A ghost profile with this email already exists");
        }

        // Create ghost user — DB unique constraint is the final safety net for TOCTOU races
        var ghost = new UserEntity(normalizedEmail, name);
        ghost.setClaimed(false);
        try {
            userRepository.saveAndFlush(ghost);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("A user with this email already exists");
        }

        // R4 ACCEPTED_RISK: Slug TOCTOU retry removed — PostgreSQL aborts tx after
        // constraint violation. SlugGenerator's 36^4 namespace makes collision negligible.
        var profile = new ProfileEntity(ghost.getId(), name, slugGenerator.generate(name));
        profile.setClaimed(false);
        profile.setCreatedByUserId(ownerUserId);
        profileRepository.saveAndFlush(profile);

        // Create BarberDetails (ghost barbers not bookable until claimed)
        var details = new BarberDetailsEntity(profile);
        details.setAcceptingNewClients(false);
        details.setVisibility(BarberVisibility.DRAFT);
        barberDetailsRepository.saveAndFlush(details);

        log.info("Ghost barber created: email={} by owner={}", normalizedEmail, ownerUserId);
        return profile;
    }

    // Ghost claiming is handled by UserBootstrapService (claimGhostUser / claimGhostUserWithOAuth2)
    // during the authentication flow. No separate claim method needed here.
}
