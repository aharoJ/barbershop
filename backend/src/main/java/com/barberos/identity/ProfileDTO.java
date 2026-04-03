package com.barberos.identity;

import java.time.Instant;
import java.util.UUID;

public record ProfileDTO(
        UUID id,
        String displayName,
        String bio,
        String photoUrl,
        String slug,
        String phoneNumber,
        String timezone,
        boolean claimed,
        BarberDetailsDTO barberDetails,
        Instant createdAt
) {
    public static ProfileDTO from(ProfileEntity profile, BarberDetailsEntity barberDetails) {
        return new ProfileDTO(
                profile.getId(),
                profile.getDisplayName(),
                profile.getBio(),
                profile.getPhotoUrl(),
                profile.getSlug(),
                profile.getPhoneNumber(),
                profile.getTimezone(),
                profile.isClaimed(),
                barberDetails != null ? BarberDetailsDTO.from(barberDetails) : null,
                profile.getCreatedAt()
        );
    }

    public static ProfileDTO from(ProfileEntity profile) {
        return from(profile, null);
    }
}
