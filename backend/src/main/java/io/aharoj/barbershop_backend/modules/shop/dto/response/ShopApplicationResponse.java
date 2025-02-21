package io.aharoj.barbershop_backend.modules.shop.dto.response;

import java.time.LocalDateTime;


public class ShopApplicationResponse {
    private Long applicationId;
    private Long barberId;
    private Long shopId;
    private String status; // e.g. "PENDING"
    private LocalDateTime appliedAt;
    private String message;

    public ShopApplicationResponse() {
    }

    public ShopApplicationResponse(Long applicationId, Long barberId, Long shopId,
            String status, LocalDateTime appliedAt, String message) {
        this.applicationId = applicationId;
        this.barberId = barberId;
        this.shopId = shopId;
        this.status = status;
        this.appliedAt = appliedAt;
        this.message = message;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
