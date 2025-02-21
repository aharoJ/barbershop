package io.aharoj.barbershop_backend.modules.shop.model.entity;

import java.time.LocalDateTime;

import io.aharoj.barbershop_backend.modules.barber.model.entity.Barber;
import io.aharoj.barbershop_backend.modules.shop.model.enums.ApplicationStatus;
import jakarta.persistence.*;

@Entity
public class ShopApplication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // The shop to which the barber is applying
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "shop_id")
  private Shop shop;

  // The barber who is applying
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "barber_id")
  private Barber barber;

  @Enumerated(EnumType.STRING)
  private ApplicationStatus status;

  private LocalDateTime appliedWhen = LocalDateTime.now();

  // Some optional message from the barber to the owner
  private String message;

  public ShopApplication() {
  }

  public ShopApplication(Shop shop, Barber barber, ApplicationStatus status,
      LocalDateTime appliedWhen, String message) {
    this.shop = shop;
    this.barber = barber;
    this.status = status;
    this.appliedWhen = appliedWhen;
    this.message = message;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Shop getShop() {
    return shop;
  }

  public void setShop(Shop shop) {
    this.shop = shop;
  }

  public Barber getBarber() {
    return barber;
  }

  public void setBarber(Barber barber) {
    this.barber = barber;
  }

  public ApplicationStatus getStatus() {
    return status;
  }

  public void setStatus(ApplicationStatus status) {
    this.status = status;
  }

  public LocalDateTime getAppliedWhen() {
    return appliedWhen;
  }

  public void setAppliedWhen(LocalDateTime appliedWhen) {
    this.appliedWhen = appliedWhen;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
