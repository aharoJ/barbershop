package io.aharoj.barbershop_backend.modules.barber.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.barber.model.entity.BarberProfile;

@Repository
public interface BarberProfileRepository extends JpaRepository<BarberProfile, Long> {

  // If you want to find by user ID
  Optional<BarberProfile> findByUserId(Long userId);

}
