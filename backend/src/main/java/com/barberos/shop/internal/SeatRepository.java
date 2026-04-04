package com.barberos.shop.internal;

import com.barberos.shop.SeatEntity;
import com.barberos.shop.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<SeatEntity, UUID> {

    List<SeatEntity> findByShopIdAndStatusNot(UUID shopId, SeatStatus excludedStatus);

    List<SeatEntity> findByShopId(UUID shopId);

    long countByShopIdAndStatus(UUID shopId, SeatStatus status);

    @Modifying
    @Query("UPDATE SeatEntity s SET s.status = com.barberos.shop.SeatStatus.REMOVED, s.removedAt = ?2 " +
           "WHERE s.shopId = ?1 AND s.status <> com.barberos.shop.SeatStatus.REMOVED")
    int removeAllByShopId(UUID shopId, Instant removedAt);
}
