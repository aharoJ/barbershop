package com.barberos.shop.internal;

import com.barberos.shop.ShopEntity;
import com.barberos.shop.ShopStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShopRepository extends JpaRepository<ShopEntity, UUID> {

    Optional<ShopEntity> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<ShopEntity> findByStatus(ShopStatus status);
}
