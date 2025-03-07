package io.aharoj.barbershop_backend.modules.barber.dto.request;

public class BarberRequest {

  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;

  private String summary;
  private String skills;
  private int experienceYears;

  // No-argument constructor is essential for data binding.
  public BarberRequest() {
  }

  public BarberRequest(String firstName, String lastName, String email, String phoneNumber, String summary,
      String skills, int experienceYears) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
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
