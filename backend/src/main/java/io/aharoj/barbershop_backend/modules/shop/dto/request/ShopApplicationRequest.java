package io.aharoj.barbershop_backend.modules.shop.dto.request;

public class ShopApplicationRequest {
    private String message; // optional note

    public ShopApplicationRequest() {
    }

    public ShopApplicationRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
