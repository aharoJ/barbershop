package io.aharoj.barbershop_backend.modules.barber.dto.request;

import jakarta.validation.constraints.*;

public class BarberProfileRequest {

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  private String phoneNumber;

  @NotBlank
  private String email;

  private String summary;
  
  private String skills;
  
  @NotBlank 
  private int experienceYears;

  public BarberProfileRequest() {
  }

  public BarberProfileRequest(@NotBlank String firstName, @NotBlank String lastName, String phoneNumber,
      @NotBlank String email, String summary, String skills, @NotBlank int experienceYears) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.summary = summary;
    this.skills = skills;
    this.experienceYears = experienceYears;
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
