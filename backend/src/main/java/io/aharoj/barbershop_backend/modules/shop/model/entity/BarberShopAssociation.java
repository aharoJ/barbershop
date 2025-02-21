package io.aharoj.barbershop_backend.modules.shop.model.entity;

import java.time.LocalDateTime;

import io.aharoj.barbershop_backend.modules.barber.model.entity.Barber;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "barber_associations")
public class BarberShopAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The shop involved in the association
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    // The Barber who is associated.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "barber_id")
    private Barber barber;

    private LocalDateTime joinedAt = LocalDateTime.now();

    private LocalDateTime leftAt; // optional, if you want to mark they parted ways

    public BarberShopAssociation() {
    }

    public BarberShopAssociation(Shop shop, Barber barber, LocalDateTime joinedAt, LocalDateTime leftAt) {
        this.shop = shop;
        this.barber = barber;
        this.joinedAt = joinedAt;
        this.leftAt = leftAt;
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

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public LocalDateTime getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }


}
