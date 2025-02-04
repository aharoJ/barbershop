package io.aharoj.barbershop_backend.modules.shop.model.entity;

import java.util.ArrayList;
import java.util.List;

import io.aharoj.barbershop_backend.modules.owner.model.entity.OwnerProfile;
import jakarta.persistence.*;

@Entity
@Table(name = "shop")
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String address;

  // The "one" side of OneToMany with seats
  @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Seat> seats = new ArrayList<>();

  // Many shops can be owned by 1 OwnerProfile
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_profile_id")
  private OwnerProfile ownerProfile;

  // Constructors
  public Shop() {
  }

  public Shop(String name, String address, OwnerProfile ownerProfile) {
    this.name = name;
    this.address = address;
    this.ownerProfile = ownerProfile;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<Seat> getSeats() {
    return seats;
  }

  public void setSeats(List<Seat> seats) {
    this.seats = seats;
  }

  public OwnerProfile getOwnerProfile() {
    return ownerProfile;
  }

  public void setOwnerProfile(OwnerProfile ownerProfile) {
    this.ownerProfile = ownerProfile;
  }
  
}
