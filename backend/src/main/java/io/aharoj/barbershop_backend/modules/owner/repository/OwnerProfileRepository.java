package io.aharoj.barbershop_backend.modules.owner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.owner.model.entity.OwnerProfile;

@Repository
public interface OwnerProfileRepository extends JpaRepository<OwnerProfile, Long> {

  Optional<OwnerProfile> findByUserId(Long userId);

}