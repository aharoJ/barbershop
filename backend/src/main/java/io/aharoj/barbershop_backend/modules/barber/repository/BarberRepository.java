package io.aharoj.barbershop_backend.modules.barber.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.barber.model.entity.Barber;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {

  // If you want to find by user ID
  Optional<Barber> findByUserId(Long userId);

}
