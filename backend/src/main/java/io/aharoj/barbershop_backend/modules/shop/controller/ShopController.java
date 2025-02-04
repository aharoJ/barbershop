package io.aharoj.barbershop_backend.modules.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.owner.model.entity.OwnerProfile;
import io.aharoj.barbershop_backend.modules.owner.repository.OwnerProfileRepository;
import io.aharoj.barbershop_backend.modules.shop.dto.request.AssignBarberRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.request.SeatRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.request.ShopRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.response.SeatResponse;
import io.aharoj.barbershop_backend.modules.shop.dto.response.ShopResponse;
import io.aharoj.barbershop_backend.modules.shop.service.ShopService;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

  private final ShopService shopService;
  private final OwnerProfileRepository ownerProfileRepository;

  @Autowired
  public ShopController(ShopService shopService, OwnerProfileRepository ownerProfileRepository) {
    this.shopService = shopService;
    this.ownerProfileRepository = ownerProfileRepository;
  }

  /**
   * Create a new Shop (only an Owner should do this).
   */
  @PostMapping
  @PreAuthorize("hasRole('OWNER')")
  public ShopResponse createShop(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ShopRequest request) {
    // 1) Find the logged-in user's OwnerProfile
    // (We assume each user with ROLE_OWNER has an OwnerProfile)
    OwnerProfile ownerProfile = ownerProfileRepository.findByUserId(userDetails.getId())
        .orElseThrow(() -> new RuntimeException("No owner profile found for user"));

    // 2) Create the shop
    return shopService.createShop(ownerProfile.getId(), request);
  }

  /**
   * Add a seat to a shop.
   */
  @PostMapping("/{shopId}/seats")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse addSeatToShop(
      @PathVariable Long shopId,
      @RequestBody SeatRequest request) {
    return shopService.addSeatToShop(shopId, request);
  }

  /**
   * Assign a barber to a seat.
   */
  @PostMapping("/{shopId}/seats/{seatId}/assign-barber")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse assignBarberToSeat(
      @PathVariable Long shopId,
      @PathVariable Long seatId,
      @RequestBody AssignBarberRequest request) {
    return shopService.assignBarberToSeat(shopId, seatId, request);
  }

  /**
   * Get shop by id (with seats).
   */
  @GetMapping("/{shopId}")
  @PreAuthorize("hasRole('OWNER') or hasRole('BARBER') or hasRole('CUSTOMER')")
  public ShopResponse getShop(@PathVariable Long shopId) {
    return shopService.getShop(shopId);
  }
}
