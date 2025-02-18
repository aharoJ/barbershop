package io.aharoj.barbershop_backend.modules.barber.dto.response;

public class BarberProfileResponse {

  private Long id;

  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;

  private String summary;
  private String skills;
  private int experienceYears;
  private Long userId;
  
  public BarberProfileResponse(Long id, String firstName, String lastName, String email, String phoneNumber,
      String summary, String skills, int experienceYears, Long userId) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.summary = summary;
    this.skills = skills;
    this.experienceYears = experienceYears;
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
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  } 





}
