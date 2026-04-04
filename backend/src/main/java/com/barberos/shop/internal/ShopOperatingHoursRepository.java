package com.barberos.shop.internal;

import com.barberos.shop.ShopOperatingHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.UUID;

public interface ShopOperatingHoursRepository extends JpaRepository<ShopOperatingHoursEntity, UUID> {

    List<ShopOperatingHoursEntity> findByShopIdOrderByDayOfWeekAscOpensAtAsc(UUID shopId);

    @Modifying
    void deleteByShopId(UUID shopId);
}
