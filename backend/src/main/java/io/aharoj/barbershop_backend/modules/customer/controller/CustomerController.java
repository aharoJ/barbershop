package io.aharoj.barbershop_backend.modules.customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.customer.dto.request.CustomerRequest;
import io.aharoj.barbershop_backend.modules.customer.dto.response.CustomerResponse;
import io.aharoj.barbershop_backend.modules.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  /**
   * Create a CustomerProfile for the currently logged-in user (ROLE_CUSTOMER).
   */
  // @PostMapping("/profile")
  @PostMapping("/me")
  @PreAuthorize("hasRole('CUSTOMER')")
  public CustomerResponse createCustomerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody CustomerRequest request) {
    return customerService.createCustomerProfile(userDetails.getId(), request);
  }

  /**
   * Get the CustomerProfile for the logged-in user (ROLE_CUSTOMER).
   */
  @GetMapping("/me")
  @PreAuthorize("hasRole('CUSTOMER')")
  public CustomerResponse getCustomerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return customerService.getCustomerProfileByUserId(userDetails.getId());
  }

  /**
   * Update the CustomerProfile for the logged-in user (ROLE_CUSTOMER).
   */
  @PutMapping("/me")
  @PreAuthorize("hasRole('CUSTOMER')")
  public CustomerResponse updateCustomerProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody CustomerRequest request) {
    return customerService.updateCustomerProfile(userDetails.getId(), request);
  }


  @DeleteMapping("/me")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<?> deleteCustomerProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    customerService.deleteCustomerProfile(userDetails.getId());
    return ResponseEntity.ok("Customer profile deleted successfully :(");
  }
}