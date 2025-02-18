package io.aharoj.barbershop_backend.modules.owner.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.aharoj.barbershop_backend.modules.auth.repository.UserRepository;
import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.owner.dto.request.OwnerProfileRequest;
import io.aharoj.barbershop_backend.modules.owner.dto.response.OwnerProfileResponse;
import io.aharoj.barbershop_backend.modules.owner.service.OwnerService;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService, UserRepository userRepository) {
    this.ownerService = ownerService;
  }

  @PostMapping("/profile")
  @PreAuthorize("hasRole('OWNER')")
  public OwnerProfileResponse createOwnerProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody OwnerProfileRequest request) {

    return ownerService.createOwnerProfile(userDetails.getId(), request);
  }

}