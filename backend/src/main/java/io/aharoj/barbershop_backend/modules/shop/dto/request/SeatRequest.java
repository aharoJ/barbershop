package io.aharoj.barbershop_backend.modules.shop.dto.request;

public class SeatRequest {
  private String seatName;

  public SeatRequest() {
  }

  public SeatRequest(String seatName) {
    this.seatName = seatName;
  }

  public String getSeatName() {
    return seatName;
  }

  public void setSeatName(String seatName) {
    this.seatName = seatName;
  }
}