package io.aharoj.barbershop_backend.modules.appointment.dto.response;

import java.time.LocalDateTime;

import io.aharoj.barbershop_backend.modules.appointment.model.enums.AppointmentStatus;

public class AppointmentResponse {
  private Long id;
  private Long customerProfileId;
  private Long barberProfileId;
  private Long shopId; // if applicable
  private LocalDateTime appointmentTime;
  private AppointmentStatus status;

  public AppointmentResponse() {
  }

  public AppointmentResponse(Long id, Long customerProfileId, Long barberProfileId,
      Long shopId, LocalDateTime appointmentTime, AppointmentStatus status) {
    this.id = id;
    this.customerProfileId = customerProfileId;
    this.barberProfileId = barberProfileId;
    this.shopId = shopId;
    this.appointmentTime = appointmentTime;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCustomerProfileId() {
    return customerProfileId;
  }

  public void setCustomerProfileId(Long customerProfileId) {
    this.customerProfileId = customerProfileId;
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

  public LocalDateTime getAppointmentTime() {
    return appointmentTime;
  }

  public void setAppointmentTime(LocalDateTime appointmentTime) {
    this.appointmentTime = appointmentTime;
  }

  public AppointmentStatus getStatus() {
    return status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }

}
