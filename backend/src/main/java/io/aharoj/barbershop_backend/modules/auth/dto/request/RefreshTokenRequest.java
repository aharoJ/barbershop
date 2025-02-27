package io.aharoj.barbershop_backend.modules.auth.dto.request;

public class RefreshTokenRequest {
  private String refreshToken;

  public RefreshTokenRequest() {
  }

  // unsure if to create constructors?

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
