package io.aharoj.barbershop_backend.modules.barber.model.entity;

import io.aharoj.barbershop_backend.modules.auth.model.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "barber_profiles")
public class BarberProfile {
  
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

  private String summary;
  private String skills;
  
  @Column(name = "years_of_experience")
  private int experienceYears;
  
  public BarberProfile() {
  }

  public BarberProfile(User user, String firstName, String lastName, String email, String phoneNumber, String summary,
      String skills, int experienceYears) {
    this.user = user;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.summary = summary;
    this.skills = skills;
    this.experienceYears = experienceYears;
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

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getSkills() {
    return skills;
  }

  public void setSkills(String skills) {
    this.skills = skills;
  }

  public int getExperienceYears() {
    return experienceYears;
  }

  public void setExperienceYears(int experienceYears) {
    this.experienceYears = experienceYears;
  }

  



  
}