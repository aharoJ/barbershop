package io.aharoj.barbershop_backend.modules.owner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.owner.model.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

  Optional<Owner> findByUserId(Long userId);

}