package com.barberos.identity;

/**
 * Public-facing barber profile DTO. Excludes PII (phone, claimed, licenseNumber).
 * Used for unauthenticated barber discovery at /api/profiles/barbers/{slug}.
 */
public record PublicBarberDTO(
        String displayName,
        String bio,
        String photoUrl,
        String slug,
        PublicBarberDetailsDTO barberDetails
) {
    public static PublicBarberDTO from(ProfileEntity profile, BarberDetailsEntity barberDetails) {
        return new PublicBarberDTO(
                profile.getDisplayName(),
                profile.getBio(),
                profile.getPhotoUrl(),
                profile.getSlug(),
                barberDetails != null ? PublicBarberDetailsDTO.from(barberDetails) : null
        );
    }
}
