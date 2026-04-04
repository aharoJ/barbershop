package com.barberos.shop;

import java.time.Instant;
import java.util.UUID;

public record ShopDTO(
        UUID id,
        String name,
        String slug,
        String description,
        String phoneNumber,
        String email,
        String website,
        String addressLine1,
        String addressLine2,
        String city,
        String stateRegion,
        String postalCode,
        String countryCode,
        String formattedAddress,
        Double latitude,
        Double longitude,
        String timezone,
        String logoUrl,
        String coverPhotoUrl,
        ShopStatus status,
        Instant createdAt
) {
    public static ShopDTO from(ShopEntity entity) {
        return new ShopDTO(
                entity.getId(),
                entity.getName(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getWebsite(),
                entity.getAddressLine1(),
                entity.getAddressLine2(),
                entity.getCity(),
                entity.getStateRegion(),
                entity.getPostalCode(),
                entity.getCountryCode(),
                entity.getFormattedAddress(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getTimezone(),
                entity.getLogoUrl(),
                entity.getCoverPhotoUrl(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
