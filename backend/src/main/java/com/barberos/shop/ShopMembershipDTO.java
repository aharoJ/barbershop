package com.barberos.shop;

import java.time.Instant;
import java.util.UUID;

public record ShopMembershipDTO(
        UUID id,
        UUID shopId,
        UUID userId,
        ShopRole role,
        MembershipStatus status,
        Instant createdAt
) {
    public static ShopMembershipDTO from(ShopMembershipEntity entity) {
        return new ShopMembershipDTO(
                entity.getId(),
                entity.getShopId(),
                entity.getUserId(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
