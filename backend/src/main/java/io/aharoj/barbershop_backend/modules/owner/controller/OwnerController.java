package io.aharoj.barbershop_backend.modules.owner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    // FRONTEND SNIPPET
    // const { data } = await apiClient.post<OwnerResponse>(
    // "/api/owners/me",payload);
  }

  /**
   * Get the OwnerProfile for the currently logged-in owner.
   */
  @GetMapping("/me")
  @PreAuthorize("hasRole('OWNER')")
  public OwnerResponse getMyOwnerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    return ownerService.getOwnerProfileByUserId(userDetails.getId());
    // FRONTEND SNIPPET
    // const { data } = await apiClient.get<OwnerResponse>(
    // `/api/owners/me`);

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
    // FRONTEND SNIPPET
    // const { data } = await apiClient.put<OwnerResponse>(
    // `/api/owners/me`, payload);
  }

  @DeleteMapping("/me")
  @PreAuthorize("hasRole('OWNER')")
  public ResponseEntity<?> deleteOwnerProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
    ownerService.deleteOwnerProfile(userDetails.getId());
    return ResponseEntity.ok("Owner profile deleted successfully.");
  }

}
