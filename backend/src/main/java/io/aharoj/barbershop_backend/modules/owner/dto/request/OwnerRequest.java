package io.aharoj.barbershop_backend.modules.owner.dto.request;

public class OwnerRequest {

  private String firstName;

  private String lastName;

  private String phoneNumber;

  // No-argument constructor is essential for data binding.
  public OwnerRequest() {
  }

  public OwnerRequest(String firstName, String lastName, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
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

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
