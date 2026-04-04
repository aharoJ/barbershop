package com.barberos.shop.internal;

import com.barberos.shop.BarberShopAssociationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BarberShopAssociationRepository extends JpaRepository<BarberShopAssociationEntity, UUID> {

    Optional<BarberShopAssociationEntity> findByBarberIdAndShopIdAndLeftAtIsNull(UUID barberId, UUID shopId);

    List<BarberShopAssociationEntity> findByShopIdAndLeftAtIsNull(UUID shopId);

    List<BarberShopAssociationEntity> findByBarberIdAndLeftAtIsNull(UUID barberId);

    boolean existsByBarberIdAndShopIdAndLeftAtIsNull(UUID barberId, UUID shopId);

    boolean existsByBarberIdAndLeftAtIsNull(UUID barberId);

    @Modifying
    @Query("UPDATE BarberShopAssociationEntity a SET a.leftAt = ?2, a.endedReason = com.barberos.shop.EndedReason.SHOP_CLOSED, " +
           "a.endedByUserId = ?3 WHERE a.shopId = ?1 AND a.leftAt IS NULL")
    int endAllActiveByShopId(UUID shopId, Instant leftAt, UUID endedByUserId);
}
