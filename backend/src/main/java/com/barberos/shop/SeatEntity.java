package com.barberos.shop;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/** D36: Named seats for capacity management. UNIQUE(shop_id, name). */
@Entity
@Table(name = "seats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "name"}))
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "shop_id", nullable = false)
    private UUID shopId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeatStatus status = SeatStatus.ACTIVE;

    @Column(name = "removed_at")
    private Instant removedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected SeatEntity() {
    }

    public SeatEntity(UUID shopId, String name) {
        this.shopId = shopId;
        this.name = name;
    }

    @PrePersist
    void prePersist() {
        var now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getShopId() { return shopId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }
    public Instant getRemovedAt() { return removedAt; }
    public void setRemovedAt(Instant removedAt) { this.removedAt = removedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
