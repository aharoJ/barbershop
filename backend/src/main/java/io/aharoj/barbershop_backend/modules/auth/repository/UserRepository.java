package io.aharoj.barbershop_backend.modules.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.aharoj.barbershop_backend.modules.auth.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String username);
}
