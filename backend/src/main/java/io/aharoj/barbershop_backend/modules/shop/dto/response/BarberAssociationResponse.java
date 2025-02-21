package io.aharoj.barbershop_backend.modules.shop.dto.response;

import java.time.LocalDateTime;

public class BarberAssociationResponse {
    private Long associationId;
    private Long barberId;
    private String barberName;
    private LocalDateTime joinedAt;

    public BarberAssociationResponse() {
    }

    public BarberAssociationResponse(Long associationId, Long barberId, String barberName, LocalDateTime joinedAt) {
        this.associationId = associationId;
        this.barberId = barberId;
        this.barberName = barberName;
        this.joinedAt = joinedAt;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }


}
