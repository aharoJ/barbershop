package com.barberos.auth.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

interface MagicLinkTokenRepository extends JpaRepository<MagicLinkTokenEntity, UUID> {

    Optional<MagicLinkTokenEntity> findByTokenHash(String tokenHash);

    long countByEmailAndCreatedAtAfter(String email, Instant after);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE magic_link_tokens SET used_at = now() WHERE token_hash = :hash AND used_at IS NULL AND expires_at > now()", nativeQuery = true)
    int consumeToken(@Param("hash") String tokenHash);

    /** D17: Invalidate all unexpired tokens for an email when a new one is requested. */
    @Modifying
    @Query(value = "UPDATE magic_link_tokens SET used_at = now() WHERE email = :email AND used_at IS NULL AND expires_at > now()", nativeQuery = true)
    int invalidateTokensForEmail(@Param("email") String email);
}
