package io.aharoj.barbershop_backend.modules.customer.dto.request;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import io.aharoj.barbershop_backend.modules.shared.model.entity.Address;
import io.aharoj.barbershop_backend.modules.shared.model.enums.Gender;
import io.aharoj.barbershop_backend.modules.shared.model.enums.MarketingNotifications;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Past;

public class CustomerProfileRequest {
  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  @Past
  private LocalDate dateOfBirth;

  private Gender gender;

  private Address address;

  @Enumerated(EnumType.STRING)
  private MarketingNotifications marketingNotifications;


    @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;

  // No-argument constructor is essential for data binding.
  public CustomerProfileRequest() {
  }

  public CustomerProfileRequest(String firstName, String lastName, String email, String phoneNumber,
      @Past LocalDate dateOfBirth, Gender gender, Address address, MarketingNotifications marketingNotifications,
      Instant createdAt, Instant updatedAt) {
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

}
