package io.aharoj.barbershop_backend.modules.image.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.aharoj.barbershop_backend.modules.image.model.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

  // Possible future need: find all images for a certain owner, 
  List<Image> findByOwnerId(Long ownerId);

  List<Image> findByBarberId(Long barberId);

  List<Image> findByCustomerId(Long customerId);

  List<Image> findByShopId(Long shopId);
}