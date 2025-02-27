package io.aharoj.barbershop_backend.modules.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.aharoj.barbershop_backend.modules.auth.model.entity.*;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  void deleteByUser(User user);
}
