package io.aharoj.barbershop_backend.modules.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.shop.model.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
  List<Seat> findAllByShopId(Long shopId);
}
