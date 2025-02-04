package io.aharoj.barbershop_backend.modules.appointment.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import io.aharoj.barbershop_backend.modules.appointment.model.enums.AppointmentStatus;
import io.aharoj.barbershop_backend.modules.barber.model.entity.BarberProfile;
import io.aharoj.barbershop_backend.modules.customer.model.entity.CustomerProfile;
import io.aharoj.barbershop_backend.modules.shop.model.entity.Shop;

@Entity
@Table(name = "appointments")
public class Appointment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // For example, 2024-01-15 09:30
  private LocalDateTime appointmentTime;

  // We can store status as a string or an enum
  @Enumerated(EnumType.STRING)
  private AppointmentStatus status;

  /**
   * A basic association: which customer booked it?
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerProfile customerProfile;

  /**
   * Which barber is this appointment with?
   * Could be an independent barber or a shop-based barber.
   * For simplicity, assume it's a BarberProfile.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "barber_id", nullable = false)
  private BarberProfile barberProfile;

  /**
   * If you want to tie it to a specific Shop (especially if the barber
   * works at multiple shops or you just want data clarity).
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shop_id")
  private Shop shop; // Could be nullable if it's an independent barber scenario

  /**
   * If you want to track which seat is used (optional)
   *
   * @ManyToOne(fetch = FetchType.LAZY)
   *                  private Seat seat;
   * 
   */
  public Appointment() {
  }

  public Appointment(LocalDateTime appointmentTime, AppointmentStatus status,
      CustomerProfile customerProfile,
      BarberProfile barberProfile,
      Shop shop) {
    this.appointmentTime = appointmentTime;
    this.status = status;
    this.customerProfile = customerProfile;
    this.barberProfile = barberProfile;
    this.shop = shop;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public CustomerProfile getCustomerProfile() {
    return customerProfile;
  }

  public void setCustomerProfile(CustomerProfile customerProfile) {
    this.customerProfile = customerProfile;
  }

  public BarberProfile getBarberProfile() {
    return barberProfile;
  }

  public void setBarberProfile(BarberProfile barberProfile) {
    this.barberProfile = barberProfile;
  }

  public Shop getShop() {
    return shop;
  }

  public void setShop(Shop shop) {
    this.shop = shop;
  }

}
