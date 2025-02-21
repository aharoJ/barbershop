package io.aharoj.barbershop_backend.modules.shop.dto.response;


public class SeatResponse {
    private Long id;
    private String seatName;
    private Long barberId;
    private String barberFullName;

    public SeatResponse() {
    }

    public SeatResponse(Long id, String seatName, Long barberId, String barberFullName) {
        this.id = id;
        this.seatName = seatName;
        this.barberId = barberId;
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

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public String getBarberFullName() {
        return barberFullName;
    }

    public void setBarberFullName(String barberFullName) {
        this.barberFullName = barberFullName;
    }

}
