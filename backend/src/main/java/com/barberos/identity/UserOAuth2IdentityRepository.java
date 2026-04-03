package com.barberos.identity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserOAuth2IdentityRepository extends JpaRepository<UserOAuth2IdentityEntity, UUID> {

    Optional<UserOAuth2IdentityEntity> findByProviderAndSubject(String provider, String subject);

    List<UserOAuth2IdentityEntity> findByUserId(UUID userId);

    long countByUserId(UUID userId);
}
