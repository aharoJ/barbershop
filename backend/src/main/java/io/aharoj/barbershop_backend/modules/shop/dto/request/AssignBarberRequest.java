package io.aharoj.barbershop_backend.modules.shop.dto.request;

public class AssignBarberRequest {
    // We'll pass in the ID of the BarberShopAssociation to assign
    private Long associationId;

    public AssignBarberRequest() {
    }

    public AssignBarberRequest(Long associationId) {
        this.associationId = associationId;
    }

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }


}
