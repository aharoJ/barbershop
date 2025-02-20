package io.aharoj.barbershop_backend.modules.owner.dto.response;

public class OwnerResponse {

  private Long OwnerId;

  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  private Long userId;

  public OwnerResponse(Long ownerId, String firstName, String lastName, String email, String phoneNumber,
      Long userId) {
    OwnerId = ownerId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.userId = userId;
  }

  public Long getOwnerId() {
    return OwnerId;
  }

  public void setOwnerId(Long ownerId) {
    OwnerId = ownerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
  
}
