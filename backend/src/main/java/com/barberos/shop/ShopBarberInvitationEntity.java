package com.barberos.shop;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/** D41: Separate invitation table for claimed barber consent flow. */
@Entity
@Table(name = "shop_barber_invitations")
public class ShopBarberInvitationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "shop_id", nullable = false)
    private UUID shopId;

    @Column(name = "barber_id", nullable = false)
    private UUID barberId;

    @Column(name = "invited_by_user_id", nullable = false)
    private UUID invitedByUserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InvitationStatus status = InvitationStatus.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "responded_at")
    private Instant respondedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    protected ShopBarberInvitationEntity() {
    }

    public ShopBarberInvitationEntity(UUID shopId, UUID barberId, UUID invitedByUserId) {
        this.shopId = shopId;
        this.barberId = barberId;
        this.invitedByUserId = invitedByUserId;
    }

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getShopId() { return shopId; }
    public UUID getBarberId() { return barberId; }
    public UUID getInvitedByUserId() { return invitedByUserId; }
    public InvitationStatus getStatus() { return status; }
    public void setStatus(InvitationStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getRespondedAt() { return respondedAt; }
    public void setRespondedAt(Instant respondedAt) { this.respondedAt = respondedAt; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
