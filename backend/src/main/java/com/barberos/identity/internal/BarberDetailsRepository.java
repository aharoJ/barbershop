package com.barberos.identity.internal;

import com.barberos.identity.BarberDetailsEntity;
import com.barberos.identity.BarberVisibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BarberDetailsRepository extends JpaRepository<BarberDetailsEntity, UUID> {

    boolean existsByProfileId(UUID profileId);

    List<BarberDetailsEntity> findByVisibility(BarberVisibility visibility);
}
