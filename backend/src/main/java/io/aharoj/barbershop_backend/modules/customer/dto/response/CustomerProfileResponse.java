package io.aharoj.barbershop_backend.modules.customer.dto.response;

public class CustomerProfileResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String phone;
  private String email;
  private Long userId;

  public CustomerProfileResponse() {
  }

  public CustomerProfileResponse(Long id, String firstName, String lastName, String phone, String email, Long userId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.userId = userId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

}
