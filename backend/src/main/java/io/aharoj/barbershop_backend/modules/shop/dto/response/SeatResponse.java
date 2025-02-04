package io.aharoj.barbershop_backend.modules.shop.dto.response;

public class SeatResponse {
  private Long id;
  private String seatName;
  private Long barberProfileId; 
  private String barberFullName; // Optional - if you want to include more detail


  public SeatResponse() {
  }


  public SeatResponse(Long id, String seatName, Long barberProfileId, String barberFullName) {
    this.id = id;
    this.seatName = seatName;
    this.barberProfileId = barberProfileId;
    this.barberFullName = barberFullName;
  }


  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public String getSeatName() {
    return seatName;
  }


  public void setSeatName(String seatName) {
    this.seatName = seatName;
  }


  public Long getBarberProfileId() {
    return barberProfileId;
  }


  public void setBarberProfileId(Long barberProfileId) {
    this.barberProfileId = barberProfileId;
  }


  public String getBarberFullName() {
    return barberFullName;
  }


  public void setBarberFullName(String barberFullName) {
    this.barberFullName = barberFullName;
  }

 
}