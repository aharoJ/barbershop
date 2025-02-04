package io.aharoj.barbershop_backend.modules.owner.dto.response;

public class OwnerProfileResponse {

  private Long id;
  private String ownerName;
  private Long userId;

  // No-arg constructor (optional but often convenient for JSON serialization)
  public OwnerProfileResponse() {
  }

  // All-arg constructor
  public OwnerProfileResponse(Long id, String ownerName, Long userId) {
    this.id = id;
    this.ownerName = ownerName;
    this.userId = userId;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
