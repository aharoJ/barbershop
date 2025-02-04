package io.aharoj.barbershop_backend.modules.shop.dto.request;

public class AssignBarberRequest {
  private Long barberId;
  private Long barberProfileId;

  public AssignBarberRequest() {
  }

public AssignBarberRequest(Long barberId, Long barberProfileId) {
    this.barberId = barberId;
    this.barberProfileId = barberProfileId;
}

public Long getBarberId() {
  return barberId;
}

public void setBarberId(Long barberId) {
  this.barberId = barberId;
}

public Long getBarberProfileId() {
  return barberProfileId;
}

public void setBarberProfileId(Long barberProfileId) {
  this.barberProfileId = barberProfileId;
}

}