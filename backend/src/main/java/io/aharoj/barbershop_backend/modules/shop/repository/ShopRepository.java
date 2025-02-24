package io.aharoj.barbershop_backend.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.owner.model.entity.Owner;
import io.aharoj.barbershop_backend.modules.shop.model.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
  // return findByOwner
  // List<Shop> getShopsByOwner(Long ownerId);
  // testing below? 
  List<Shop> findByOwner(Owner owner);
  

}
