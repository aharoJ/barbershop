package com.barberos.identity;

import com.barberos.identity.internal.ProfileService;
import com.barberos.identity.internal.UserDeletionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final UserResolver userResolver;
    private final ProfileService profileService;
    private final BarberEnrollmentService enrollmentService;
    private final UserDeletionService deletionService;

    public ProfileController(UserResolver userResolver,
                              ProfileService profileService,
                              BarberEnrollmentService enrollmentService,
                              UserDeletionService deletionService) {
        this.userResolver = userResolver;
        this.profileService = profileService;
        this.enrollmentService = enrollmentService;
        this.deletionService = deletionService;
    }

    /** Get current user's profile with barber details if enrolled. */
    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(401).build();
        }

        UUID userId = userResolver.resolveUserId(authentication);
        ProfileEntity profile = profileService.getByUserId(userId);
        BarberDetailsEntity barberDetails = profileService.getBarberDetails(profile.getId());
        return ResponseEntity.ok(ProfileDTO.from(profile, barberDetails));
    }

    /** Update current user's profile. */
    @PatchMapping("/me")
    public ResponseEntity<ProfileDTO> updateProfile(Authentication authentication,
                                                     @Valid @RequestBody ProfileUpdateRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        ProfileEntity profile = profileService.updateProfile(
                userId, request.displayName(), request.bio(),
                request.phoneNumber(), request.timezone());
        BarberDetailsEntity barberDetails = profileService.getBarberDetails(profile.getId());
        return ResponseEntity.ok(ProfileDTO.from(profile, barberDetails));
    }

    /** Enroll current user as a barber (D24). */
    @PostMapping("/me/enroll-barber")
    public ResponseEntity<BarberDetailsDTO> enrollAsBarber(Authentication authentication,
                                                            @Valid @RequestBody BarberEnrollmentRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        BarberDetailsEntity details = enrollmentService.enrollAsBarber(
                userId, request.specialties(), request.experienceYears(), request.licenseNumber());
        return ResponseEntity.ok(BarberDetailsDTO.from(details));
    }

    /** Delete current user's account (D29: soft delete + anonymize). Invalidates session. */
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAccount(Authentication authentication,
                                               HttpServletRequest request) {
        UUID userId = userResolver.resolveUserId(authentication);
        deletionService.deleteUser(userId);
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return ResponseEntity.noContent().build();
    }

    /** Public barber profile by slug (D31: only LISTED barbers). No auth required. */
    @GetMapping("/barbers/{slug}")
    public ResponseEntity<PublicBarberDTO> publicBarberProfile(@PathVariable String slug) {
        var result = profileService.getPublicBarberBySlug(slug);
        return ResponseEntity.ok(PublicBarberDTO.from(result.profile(), result.barberDetails()));
    }

    // Request records

    public record ProfileUpdateRequest(
            @Size(max = 255) String displayName,
            @Size(max = 5000) String bio,
            @Size(max = 50) String phoneNumber,
            @Size(max = 50) String timezone
    ) {}

    public record BarberEnrollmentRequest(
            String[] specialties,
            @Min(0) @Max(100) Integer experienceYears,
            @Size(max = 100) String licenseNumber
    ) {}
}
