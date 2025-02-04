package io.aharoj.barbershop_backend.modules.shop.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ShopResponse {
  private Long id;
  private String name;
  private String address;
  private List<SeatResponse> seats = new ArrayList<>();

  public ShopResponse() {
  }

  public ShopResponse(Long id, String name, String address, List<SeatResponse> seats) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.seats = seats;
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

  public List<SeatResponse> getSeats() {
    return seats;
  }

  public void setSeats(List<SeatResponse> seats) {
    this.seats = seats;
  }

}
