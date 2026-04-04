package com.barberos.shop;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/**
 * D34: Sole source of truth for shop ownership. UNIQUE(shop_id, user_id).
 * D42: At least one OWNER required per non-deleted shop.
 * D43: Status=REVOKED on shop deletion.
 */
@Entity
@Table(name = "shop_memberships",
        uniqueConstraints = @UniqueConstraint(columnNames = {"shop_id", "user_id"}))
public class ShopMembershipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "shop_id", nullable = false)
    private UUID shopId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ShopRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MembershipStatus status = MembershipStatus.ACTIVE;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @Column(name = "revoked_by_user_id")
    private UUID revokedByUserId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected ShopMembershipEntity() {
    }

    public ShopMembershipEntity(UUID shopId, UUID userId, ShopRole role) {
        this.shopId = shopId;
        this.userId = userId;
        this.role = role;
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
    public UUID getUserId() { return userId; }
    public ShopRole getRole() { return role; }
    public void setRole(ShopRole role) { this.role = role; }
    public MembershipStatus getStatus() { return status; }
    public void setStatus(MembershipStatus status) { this.status = status; }
    public Instant getRevokedAt() { return revokedAt; }
    public void setRevokedAt(Instant revokedAt) { this.revokedAt = revokedAt; }
    public UUID getRevokedByUserId() { return revokedByUserId; }
    public void setRevokedByUserId(UUID revokedByUserId) { this.revokedByUserId = revokedByUserId; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
