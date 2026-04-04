package com.barberos.shop;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

/**
 * D37: Shop hours as local TIME (not UTC). C53: DST-safe.
 * Multiple rows per day allowed for split shifts.
 */
@Entity
@Table(name = "shop_operating_hours")
public class ShopOperatingHoursEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "shop_id", nullable = false)
    private UUID shopId;

    @Column(name = "day_of_week", nullable = false, columnDefinition = "smallint")
    private short dayOfWeek;

    @Column(name = "opens_at", nullable = false)
    private LocalTime opensAt;

    @Column(name = "closes_at", nullable = false)
    private LocalTime closesAt;

    protected ShopOperatingHoursEntity() {
    }

    public ShopOperatingHoursEntity(UUID shopId, short dayOfWeek, LocalTime opensAt, LocalTime closesAt) {
        this.shopId = shopId;
        this.dayOfWeek = dayOfWeek;
        this.opensAt = opensAt;
        this.closesAt = closesAt;
    }

    public UUID getId() { return id; }
    public UUID getShopId() { return shopId; }
    public short getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(short dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public LocalTime getOpensAt() { return opensAt; }
    public void setOpensAt(LocalTime opensAt) { this.opensAt = opensAt; }
    public LocalTime getClosesAt() { return closesAt; }
    public void setClosesAt(LocalTime closesAt) { this.closesAt = closesAt; }
}
