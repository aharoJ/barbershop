package io.aharoj.barbershop_backend.modules.auth.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignupRequest {

  @NotBlank
  private String username;

  @Email
  @NotBlank
  private String email;

  @NotBlank
  // @Size(min = 6) -- LATER INTEGRATION
  private String password;

  // @Enumerated(EnumType.STRING) // come back and CHECK IF THIS WORKED OUT
  private Set<String> role; // Roles are strings like "owner", "barber"

  public SignupRequest() {
  }

  public SignupRequest(@NotNull String username, @NotNull String email, @NotNull String password, Set<String> role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRole() {
    return role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }

}
