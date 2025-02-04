package io.aharoj.barbershop_backend.modules.auth.dto.response;



import java.util.List;

import io.aharoj.barbershop_backend.modules.auth.model.enums.RoleName;

/**
 * JwtResponse
 */
public class JwtResponse {
  private String token;
  private String type = "Bearer"; // The type of token (e.g., "Bearer") is a textual piece of information
  private Long id;
  private String username;
  private String email;
  private List<String> roles;

  public JwtResponse() {
  }

  public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
    this.token = token;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

}
