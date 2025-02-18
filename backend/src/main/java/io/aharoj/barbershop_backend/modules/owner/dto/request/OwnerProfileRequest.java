package io.aharoj.barbershop_backend.modules.owner.dto.request;

public class OwnerProfileRequest {
  
  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  // No-argument constructor is essential for data binding.
  public OwnerProfileRequest() {
  }

  public OwnerProfileRequest(String firstName, String lastName, String email, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
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

  

  

}
