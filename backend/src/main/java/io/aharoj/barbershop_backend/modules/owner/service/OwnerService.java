package io.aharoj.barbershop_backend.modules.owner.service;

import io.aharoj.barbershop_backend.modules.owner.dto.request.OwnerRequest;
import io.aharoj.barbershop_backend.modules.owner.dto.response.OwnerResponse;

public interface OwnerService {

  /**
   * Create a new OwnerProfile for a given user.
   *
   * @param userId  the ID of the user who is creating the OwnerProfile
   * @param request the data needed to create the OwnerProfile
   * @return an OwnerProfileResponse with the newly created OwnerProfile data
   * @sidenote This ensures the response remains tailored for the client.
   */
  OwnerResponse createOwnerProfile(Long userId, OwnerRequest request);

  /**
   * Retrieve an OwnerProfile by user ID.
   *
   * @param userId the ID of the user whose OwnerProfile we want
   * @return an OwnerProfileResponse if found; otherwise throws an exception 
   * @sidenote This ensures the entity remains an internal detail
   */
  OwnerResponse getOwnerProfileByUserId(Long userId);


    /**
     * Update the OwnerProfile for a given user.
     *
     * @param userId  the ID of the user whose profile should be updated
     * @param request the new data for the OwnerProfile
     * @return an OwnerProfileResponse containing the updated data
     */
    OwnerResponse updateOwnerProfile(Long userId, OwnerRequest request);

    // created... come back to annotate nicely 
    void deleteOwnerProfile(Long userId);
    
}
