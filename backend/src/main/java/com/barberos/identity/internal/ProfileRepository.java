package com.barberos.identity.internal;

import com.barberos.identity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {

    Optional<ProfileEntity> findByUserId(UUID userId);

    Optional<ProfileEntity> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsByUserId(UUID userId);
}
