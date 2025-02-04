package io.aharoj.barbershop_backend.modules.owner.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.aharoj.barbershop_backend.modules.auth.model.entity.User;
import io.aharoj.barbershop_backend.modules.auth.repository.UserRepository;
import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.owner.dto.request.OwnerProfileRequest;
import io.aharoj.barbershop_backend.modules.owner.dto.response.OwnerProfileResponse;
import io.aharoj.barbershop_backend.modules.owner.service.OwnerService;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

  private final OwnerService ownerService;

  private final UserRepository userRepository;

  // @PostMapping("/profile")
  // public HttpResponse<OwnerProfileResponse> createOwnerProfile() {
  // }

  public OwnerController(OwnerService ownerService, UserRepository userRepository) {
    this.ownerService = ownerService;
    this.userRepository = userRepository;
  }

  @PostMapping("/profile")
public OwnerProfileResponse createOwnerProfile(
    @AuthenticationPrincipal UserDetailsImpl userDetails, 
    @RequestBody OwnerProfileRequest request) {

    // userDetails should NOT be null if your token is valid and the user is authenticated
    if (userDetails == null) {
        throw new RuntimeException("No authenticated user found!");
    }

    // Then fetch the actual JPA User from DB
    User dbUser = userRepository.findById(userDetails.getId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    return ownerService.createOwnerProfile(dbUser, request);
}


}