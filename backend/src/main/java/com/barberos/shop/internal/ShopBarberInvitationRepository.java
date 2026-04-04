package com.barberos.shop.internal;

import com.barberos.shop.InvitationStatus;
import com.barberos.shop.ShopBarberInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShopBarberInvitationRepository extends JpaRepository<ShopBarberInvitationEntity, UUID> {

    List<ShopBarberInvitationEntity> findByBarberIdAndStatus(UUID barberId, InvitationStatus status);

    List<ShopBarberInvitationEntity> findByShopId(UUID shopId);

    List<ShopBarberInvitationEntity> findByShopIdAndStatus(UUID shopId, InvitationStatus status);

    Optional<ShopBarberInvitationEntity> findByShopIdAndBarberIdAndStatus(UUID shopId, UUID barberId, InvitationStatus status);

    boolean existsByShopIdAndBarberIdAndStatus(UUID shopId, UUID barberId, InvitationStatus status);

    /** Expiry-aware check: match PENDING invitations that haven't expired (NULL expiresAt = never expires). */
    @org.springframework.data.jpa.repository.Query(
            "SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM ShopBarberInvitationEntity i " +
            "WHERE i.shopId = ?1 AND i.barberId = ?2 AND i.status = ?3 " +
            "AND (i.expiresAt IS NULL OR i.expiresAt > ?4)")
    boolean existsPendingNonExpired(UUID shopId, UUID barberId, InvitationStatus status, Instant now);
}
