package io.aharoj.barbershop_backend.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.barber.model.entity.Barber;
import io.aharoj.barbershop_backend.modules.shop.model.entity.BarberShopAssociation;
import io.aharoj.barbershop_backend.modules.shop.model.entity.Shop;

@Repository
public interface BarberShopAssociationRepository extends JpaRepository<BarberShopAssociation, Long> {

  List<BarberShopAssociation> findByShopId(Long shopId);

  // If you want to check if a barber is already in a shop
  boolean existsByShopAndBarber(Shop shop, Barber barber);
}
