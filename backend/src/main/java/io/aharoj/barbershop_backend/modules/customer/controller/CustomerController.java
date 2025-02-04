package io.aharoj.barbershop_backend.modules.customer.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.customer.dto.request.CustomerProfileRequest;
import io.aharoj.barbershop_backend.modules.customer.dto.response.CustomerProfileResponse;
import io.aharoj.barbershop_backend.modules.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  /**
   * Create a CustomerProfile for the currently logged-in user with ROLE_CUSTOMER.
   */
  @PostMapping("/profile")
  @PreAuthorize("hasRole('CUSTOMER')")
  public CustomerProfileResponse createCustomerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody CustomerProfileRequest request) {
    return customerService.createCustomerProfile(userDetails.getId(), request);
  }

  /**
   * Get the CustomerProfile for the logged-in user with ROLE_CUSTOMER.
   */
  @GetMapping("/profile")
  @PreAuthorize("hasRole('CUSTOMER')")
  public CustomerProfileResponse getCustomerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return customerService.getCustomerProfileByUserId(userDetails.getId());
  }

  /**
   * Update the CustomerProfile for the logged-in user with ROLE_CUSTOMER.
   */
  @PutMapping("/profile")
  @PreAuthorize("hasRole('CUSTOMER')")
  public CustomerProfileResponse updateCustomerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody CustomerProfileRequest request) {
    return customerService.updateCustomerProfile(userDetails.getId(), request);
  }
}
