package io.aharoj.barbershop_backend.modules.shop.model.entity;

import io.aharoj.barbershop_backend.modules.barber.model.entity.Barber;
import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatName;

    /**
     * For simplicity, we store just a "barberId" here.
     * If your design uses a BarberProfile entity, 
     * you could map a real relationship. 
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "barber_profile_id")
    private Barber barberProfile;

    // The "many" side of OneToMany
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    // Constructors
    public Seat() {}

    public Seat(String seatName, Shop shop) {
        this.seatName = seatName;
        this.shop = shop;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Barber getBarberProfile() {
        return barberProfile;
    }

    public void setBarberProfile(Barber barberProfile) {
        this.barberProfile = barberProfile;
    }
}
