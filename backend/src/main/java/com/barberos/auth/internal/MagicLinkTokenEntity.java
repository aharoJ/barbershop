package com.barberos.auth.internal;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "magic_link_tokens")
class MagicLinkTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "token_hash", nullable = false, unique = true)
    private String tokenHash;

    @Column(nullable = false)
    private String email;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "used_at")
    private Instant usedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected MagicLinkTokenEntity() {
    }

    MagicLinkTokenEntity(String tokenHash, String email, Instant expiresAt) {
        this.tokenHash = tokenHash;
        this.email = email;
        this.expiresAt = expiresAt;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }

    UUID getId() {
        return id;
    }

    String getTokenHash() {
        return tokenHash;
    }

    String getEmail() {
        return email;
    }

    Instant getExpiresAt() {
        return expiresAt;
    }

    Instant getUsedAt() {
        return usedAt;
    }

    void markUsed() {
        this.usedAt = Instant.now();
    }

    boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    boolean isUsed() {
        return usedAt != null;
    }

    Instant getCreatedAt() {
        return createdAt;
    }
}
