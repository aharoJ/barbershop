package io.aharoj.barbershop_backend.modules.image.dto.request;

public class ImageUploadRequest {
  private String description;

  public ImageUploadRequest() {
  }

  public ImageUploadRequest(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  
}
