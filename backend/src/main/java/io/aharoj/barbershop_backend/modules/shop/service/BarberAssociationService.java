package io.aharoj.barbershop_backend.modules.shop.service;

public interface BarberAssociationService {

    /**
     * Terminate an existing barber association.
     *
     * @param ownerId       the ID of the owner initiating the termination
     * @param associationId the ID of the barber association to be terminated
     * @sidenote This method encapsulates the business logic required to properly
     *           end a barber's association with a shop.
     */
    void terminateAssociation(Long ownerId, Long associationId);
}