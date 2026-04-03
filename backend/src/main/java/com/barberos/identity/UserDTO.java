package com.barberos.identity;

import java.time.Instant;
import java.util.UUID;

public record UserDTO(UUID id, String email, String name, Instant createdAt) {

    public static UserDTO from(UserEntity user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt());
    }
}
