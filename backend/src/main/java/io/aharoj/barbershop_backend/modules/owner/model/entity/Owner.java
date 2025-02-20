package io.aharoj.barbershop_backend.modules.owner.model.entity;

import io.aharoj.barbershop_backend.modules.auth.model.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "owner_profile")
public class Owner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private User user;

  private String firstName;

  private String lastName;
  
  private String email;

  private String phoneNumber;

  public Owner() {
  }

  public Owner(User user, String firstName, String lastName,
      String phoneNumber) {
    this.user = user;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = user.getEmail(); // Set from user, not request
    this.phoneNumber = phoneNumber;
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

  // removed setEmail() design choice 
  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
