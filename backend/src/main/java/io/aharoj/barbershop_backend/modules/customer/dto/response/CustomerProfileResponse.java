package io.aharoj.barbershop_backend.modules.customer.dto.response;

import java.time.Instant;
import java.time.LocalDate;

import io.aharoj.barbershop_backend.modules.shared.model.entity.Address;
import io.aharoj.barbershop_backend.modules.shared.model.enums.Gender;
import io.aharoj.barbershop_backend.modules.shared.model.enums.MarketingNotifications;

public class CustomerProfileResponse {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private LocalDate dateOfBirth;
  private Gender gender;
  private Address address;
  private MarketingNotifications marketingNotifications;
  private Instant createdAt;
  private Instant updatedAt;
  private Long userId; // to show which user this belongs to (backend USEFUL)

  public CustomerProfileResponse() {
  }

  public CustomerProfileResponse(Long id, String firstName, String lastName, String email, String phoneNumber,
      LocalDate dateOfBirth, Gender gender, Address address, MarketingNotifications marketingNotifications,
      Instant createdAt, Instant updatedAt, Long userId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.address = address;
    this.marketingNotifications = marketingNotifications;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public MarketingNotifications getMarketingNotifications() {
    return marketingNotifications;
  }

  public void setMarketingNotifications(MarketingNotifications marketingNotifications) {
    this.marketingNotifications = marketingNotifications;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  
}
