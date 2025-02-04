package io.aharoj.barbershop_backend.modules.shop.model.entity;

import io.aharoj.barbershop_backend.modules.barber.model.entity.BarberProfile;
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
    private BarberProfile barberProfile;

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

    public BarberProfile getBarberProfile() {
        return barberProfile;
    }

    public void setBarberProfile(BarberProfile barberProfile) {
        this.barberProfile = barberProfile;
    }
}
