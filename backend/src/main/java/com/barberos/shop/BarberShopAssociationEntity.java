package com.barberos.shop;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/**
 * D35: Multi-shop barbers, historical rows, partial unique index.
 * D36: Nullable default_seat_id.
 * D33: barber_id FK -> barber_details(profile_id).
 */
@Entity
@Table(name = "barber_shop_associations")
public class BarberShopAssociationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "barber_id", nullable = false)
    private UUID barberId;

    @Column(name = "shop_id", nullable = false)
    private UUID shopId;

    @Column(name = "default_seat_id")
    private UUID defaultSeatId;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private Instant joinedAt;

    @Column(name = "left_at")
    private Instant leftAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "ended_reason", length = 20)
    private EndedReason endedReason;

    @Column(name = "ended_by_user_id")
    private UUID endedByUserId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected BarberShopAssociationEntity() {
    }

    public BarberShopAssociationEntity(UUID barberId, UUID shopId) {
        this.barberId = barberId;
        this.shopId = shopId;
        this.joinedAt = Instant.now();
    }

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
        if (this.joinedAt == null) {
            this.joinedAt = this.createdAt;
        }
    }

    public UUID getId() { return id; }
    public UUID getBarberId() { return barberId; }
    public UUID getShopId() { return shopId; }
    public UUID getDefaultSeatId() { return defaultSeatId; }
    public void setDefaultSeatId(UUID defaultSeatId) { this.defaultSeatId = defaultSeatId; }
    public Instant getJoinedAt() { return joinedAt; }
    public Instant getLeftAt() { return leftAt; }
    public void setLeftAt(Instant leftAt) { this.leftAt = leftAt; }
    public EndedReason getEndedReason() { return endedReason; }
    public void setEndedReason(EndedReason endedReason) { this.endedReason = endedReason; }
    public UUID getEndedByUserId() { return endedByUserId; }
    public void setEndedByUserId(UUID endedByUserId) { this.endedByUserId = endedByUserId; }
    public Instant getCreatedAt() { return createdAt; }

    public boolean isActive() {
        return this.leftAt == null;
    }
}
