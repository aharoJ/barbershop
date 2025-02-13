package io.aharoj.barbershop_backend.modules.customer.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.aharoj.barbershop_backend.modules.auth.model.entity.User;
import io.aharoj.barbershop_backend.modules.shared.model.entity.Address;
import io.aharoj.barbershop_backend.modules.shared.model.enums.Gender;
import io.aharoj.barbershop_backend.modules.shared.model.enums.MarketingNotifications;

@Entity
@Table(name = "customer_profiles")
@EntityListeners(AuditingEntityListener.class) // ← Crucial for auto timestamps
public class CustomerProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Link to the "users" table
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private User user;

  private String firstName;
  private String lastName;

  // @Column(unique = true) -- NOT FOR NOW
  private String email;

  // @Column(unique = true) -- NOT FOR NOW
  private String phoneNumber;

  @Past
  private LocalDate dateOfBirth;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private MarketingNotifications marketingNotifications;

  @Embedded
  private Address address;

  // Timestamps
  @CreatedDate
  @Column(updatable = false) // Prevent manual updates
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;

  public CustomerProfile() {
  }

  public CustomerProfile(User user, String firstName, String lastName, String email, String phoneNumber,
      @Past LocalDate dateOfBirth, Gender gender, MarketingNotifications marketingNotifications, Address address,
      Instant createdAt, Instant updatedAt) {
    this.user = user;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.marketingNotifications = marketingNotifications;
    this.address = address;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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

  public MarketingNotifications getMarketingNotifications() {
    return marketingNotifications;
  }

  public void setMarketingNotifications(MarketingNotifications marketingNotifications) {
    this.marketingNotifications = marketingNotifications;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
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
