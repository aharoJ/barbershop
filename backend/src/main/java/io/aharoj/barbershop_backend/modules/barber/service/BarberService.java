package io.aharoj.barbershop_backend.modules.barber.service;

import io.aharoj.barbershop_backend.modules.barber.dto.request.BarberRequest;
import io.aharoj.barbershop_backend.modules.barber.dto.response.BarberResponse;


public interface BarberService {

    /**
     * Create a new BarberProfile for a user with the ROLE_BARBER.
     *
     * @param userId  the ID of the user for whom the BarberProfile is being created
     * @param request the details of the BarberProfile to be created
     * @return a BarberProfileResponse containing the details of the newly created
     *         BarberProfile
     * @note for client
     */
    BarberResponse createBarberProfile(Long userId, BarberRequest request);

    /**
     * Retrieve an existing BarberProfile by the user ID.
     *
     * @param userId the ID of the user whose BarberProfile is to be retrieved
     * @return a BarberProfileResponse containing the details of the BarberProfile
     * @note for client
     */
    BarberResponse getBarberProfileByUserId(Long userId);

    /**
     * Update the BarberProfile for a user if needed.
     *
     * @param userId  the ID of the user whose BarberProfile is to be updated
     * @param request the updated details for the BarberProfile
     * @return a BarberProfileResponse containing the updated BarberProfile details
     * @note for client
     */
    BarberResponse updateBarberProfile(Long userId, BarberRequest request);

    void deleteBarberProfile(Long userId);
}
