package com.barberos.identity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_oauth2_identities")
public class UserOAuth2IdentityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected UserOAuth2IdentityEntity() {}

    public UserOAuth2IdentityEntity(UUID userId, String provider, String subject, String email) {
        this.userId = userId;
        this.provider = provider;
        this.subject = subject;
        this.email = email;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getProvider() { return provider; }
    public String getSubject() { return subject; }
    public String getEmail() { return email; }
    public Instant getCreatedAt() { return createdAt; }
}
