package io.aharoj.barbershop_backend.modules.image.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import io.aharoj.barbershop_backend.modules.image.model.entity.Image;

public interface ImageService {

  /**
   * Upload an image for a specific owner.
   * 
   * @param ownerId the ID of the owner
   * @param file    the MultipartFile from the request
   * @return the created Image entity
   * @throws IOException if file saving fails
   */
  Image uploadForOwner(Long ownerId, MultipartFile file) throws IOException;

  /**
   * Upload an image for a specific barber.
   */
  Image uploadForBarber(Long barberId, MultipartFile file) throws IOException;

  /**
   * Upload an image for a specific customer.
   */
  Image uploadForCustomer(Long customerId, MultipartFile file) throws IOException;

  /**
   * Upload an image for a specific shop.
   */
  Image uploadForShop(Long shopId, MultipartFile file) throws IOException;

  /**
   * Retrieves a stored file as a Resource by fileName.
   * 
   * @param fileName the saved file name on disk
   * @return the Resource for streaming
   * @throws IOException if the file is not found or fails to read
   */
  org.springframework.core.io.Resource loadFileAsResource(String fileName) throws IOException;
}
