package io.aharoj.barbershop_backend.modules.appointment.dto.request;

import java.time.LocalDateTime;

public class AppointmentRequest {
  private Long barberProfileId;
  private Long shopId; // optional if you want them to pick a specific shop
  private LocalDateTime desiredTime;

  public AppointmentRequest() {
  }

  public AppointmentRequest(Long barberProfileId, Long shopId, LocalDateTime desiredTime) {
    this.barberProfileId = barberProfileId;
    this.shopId = shopId;
    this.desiredTime = desiredTime;
  }

  public Long getBarberProfileId() {
    return barberProfileId;
  }

  public void setBarberProfileId(Long barberProfileId) {
    this.barberProfileId = barberProfileId;
  }

  public Long getShopId() {
    return shopId;
  }

  public void setShopId(Long shopId) {
    this.shopId = shopId;
  }

  public LocalDateTime getDesiredTime() {
    return desiredTime;
  }

  public void setDesiredTime(LocalDateTime desiredTime) {
    this.desiredTime = desiredTime;
  }

}
