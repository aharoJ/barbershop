package io.aharoj.barbershop_backend.modules.customer.model.entity;

import jakarta.persistence.*;

import io.aharoj.barbershop_backend.modules.auth.model.entity.User;

@Entity
@Table(name = "customer_profiles")
public class CustomerProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private User user;

  private String firstName;
  private String lastName;
  @Column(name = "phone_number")
  private String phoneNumber;
  private String email;

  public CustomerProfile() {
  }

  public CustomerProfile(User user, String firstName, String lastName, String phoneNumber, String email) {
    this.user = user;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
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

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
