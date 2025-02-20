package io.aharoj.barbershop_backend.modules.owner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.aharoj.barbershop_backend.modules.auth.repository.UserRepository;
import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.owner.dto.request.OwnerRequest;
import io.aharoj.barbershop_backend.modules.owner.dto.response.OwnerResponse;
import io.aharoj.barbershop_backend.modules.owner.service.OwnerService;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService, UserRepository userRepository) {
    this.ownerService = ownerService;
  }

  /**
   * Create an OwnerProfile for the currently logged-in user with ROLE_OWNER.
   */
  @PostMapping("/me")
  @PreAuthorize("hasRole('OWNER')")
  public OwnerResponse createOwnerProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody OwnerRequest request) {

    return ownerService.createOwnerProfile(userDetails.getId(), request);
  }

  /**
   * Get the OwnerProfile for the currently logged-in owner.
   */
  @GetMapping("/me")
  @PreAuthorize("hasRole('OWNER')")
  public OwnerResponse getMyOwnerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ownerService.getOwnerProfileByUserId(userDetails.getId());
  }

  /**
   * Update the OwnerProfile for the logged-in owner.
   */
  @PutMapping("/me")
  @PreAuthorize("hasRole('OWNER')")
  public OwnerResponse updateOwnerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody OwnerRequest request) {
    return ownerService.updateOwnerProfile(userDetails.getId(), request);
  }

  /**
   * (Optional) Retrieve another owner's profile by ID if needed for some
   * management
   * function. Adjust PreAuthorize accordingly.
   */
  
  // @GetMapping("/{ownerId}")
  // @PreAuthorize("hasRole('OWNER')")
  // public OwnerResponse getOwnerProfileById(@PathVariable Long ownerId) {
  //   return ownerService.getOwnerProfileByUserId(ownerId);
  // }

}