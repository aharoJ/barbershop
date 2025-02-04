package io.aharoj.barbershop_backend.modules.customer.service;

import io.aharoj.barbershop_backend.modules.customer.dto.request.CustomerProfileRequest;
import io.aharoj.barbershop_backend.modules.customer.dto.response.CustomerProfileResponse;

public interface CustomerService {

    /**
     * Create a new CustomerProfile for a user.
     *
     * @param userId  the ID of the user for whom the CustomerProfile is being
     *                created
     * @param request the details of the CustomerProfile to be created
     * @return a CustomerProfileResponse containing the details of the newly created
     *         CustomerProfile
     * @note for client
     */
    CustomerProfileResponse createCustomerProfile(Long userId, CustomerProfileRequest request);

    /**
     * Retrieve an existing CustomerProfile by the user ID.
     *
     * @param userId the ID of the user whose CustomerProfile is to be retrieved
     * @return a CustomerProfileResponse containing the details of the
     *         CustomerProfile
     * @note for client
     */
    CustomerProfileResponse getCustomerProfileByUserId(Long userId);

    /**
     * Update the CustomerProfile for a user if needed.
     *
     * @param userId  the ID of the user whose CustomerProfile is to be updated
     * @param request the updated details for the CustomerProfile
     * @return a CustomerProfileResponse containing the updated CustomerProfile
     *         details
     * @note for client
     */
    CustomerProfileResponse updateCustomerProfile(Long userId, CustomerProfileRequest request);
}
