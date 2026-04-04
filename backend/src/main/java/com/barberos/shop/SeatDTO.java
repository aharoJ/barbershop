package com.barberos.shop;

import java.util.UUID;

public record SeatDTO(UUID id, UUID shopId, String name, SeatStatus status) {
    public static SeatDTO from(SeatEntity entity) {
        return new SeatDTO(entity.getId(), entity.getShopId(), entity.getName(), entity.getStatus());
    }
}
