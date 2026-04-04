package com.barberos.shop.internal;

import com.barberos.shop.MembershipStatus;
import com.barberos.shop.ShopMembershipEntity;
import com.barberos.shop.ShopRole;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShopMembershipRepository extends JpaRepository<ShopMembershipEntity, UUID> {

    Optional<ShopMembershipEntity> findByShopIdAndUserIdAndStatus(UUID shopId, UUID userId, MembershipStatus status);

    List<ShopMembershipEntity> findByShopIdAndStatus(UUID shopId, MembershipStatus status);

    List<ShopMembershipEntity> findByUserIdAndStatus(UUID userId, MembershipStatus status);

    long countByShopIdAndRoleAndStatus(UUID shopId, ShopRole role, MembershipStatus status);

    /** D42: Row-level lock on OWNER rows to prevent last-owner TOCTOU race.
     *  PostgreSQL rejects FOR UPDATE with aggregate functions — must lock rows, count in memory. */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM ShopMembershipEntity m WHERE m.shopId = ?1 AND m.role = ?2 AND m.status = ?3")
    List<ShopMembershipEntity> findByShopIdAndRoleAndStatusForUpdate(UUID shopId, ShopRole role, MembershipStatus status);

    boolean existsByShopIdAndUserIdAndStatus(UUID shopId, UUID userId, MembershipStatus status);

    @Modifying
    @Query("UPDATE ShopMembershipEntity m SET m.status = com.barberos.shop.MembershipStatus.REVOKED, " +
           "m.revokedAt = ?2, m.revokedByUserId = ?3 " +
           "WHERE m.shopId = ?1 AND m.status = com.barberos.shop.MembershipStatus.ACTIVE")
    int revokeAllByShopId(UUID shopId, Instant revokedAt, UUID revokedByUserId);
}
