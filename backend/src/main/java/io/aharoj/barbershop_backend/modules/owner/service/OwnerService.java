package io.aharoj.barbershop_backend.modules.owner.service;

import io.aharoj.barbershop_backend.modules.auth.model.entity.User;
import io.aharoj.barbershop_backend.modules.owner.dto.request.OwnerProfileRequest;
import io.aharoj.barbershop_backend.modules.owner.dto.response.OwnerProfileResponse;
import io.aharoj.barbershop_backend.modules.owner.model.entity.OwnerProfile;

public interface OwnerService {

  /**
   * Create owner Profiles
   *
   * @param user    creating the owner profile
   * @param request owner profile request
   *
   * @returns owner profile response
   *
   * @sidenote This ensures the response remains tailored for the client.
   */
  OwnerProfileResponse createOwnerProfile(User user, OwnerProfileRequest request);

  /**
   * Retrieve owner profile by user ID
   *
   * @param id the ID of the user whose owner profile is being retrieved
   * @return the OwnerProfile entity representing the owner's profile
   *
   * @sidenote This ensures the entity remains an internal detail
   */
  OwnerProfile getOwnerProfileByUserId(Long userId);
}
