package io.aharoj.barbershop_backend.modules.shop.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
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

  public ShopController(ShopService shopService) {
    this.shopService = shopService;
  }

  /**
   * Public endpoint for barbers or anyone to see the list of shops.
   */
  @GetMapping
  public List<ShopResponse> listShops() {
    return shopService.getAllShops();
  }

  /**
   * Owner creates a new Shop.
   */
  @PostMapping("/owner/create")
  @PreAuthorize("hasRole('OWNER')")
  public ShopResponse createShop(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ShopRequest request) {
    return shopService.createShop(userDetails.getId(), request);
  }

  /**
   * Get details of a specific shop (public or restricted, your call).
   */
  @GetMapping("/{shopId}")
  public ShopResponse getShopDetails(@PathVariable Long shopId) {
    return shopService.getShopById(shopId);
  }

  /**
   * Add a new seat to a shop (OWNER).
   */
  @PostMapping("/{shopId}/seats")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse addSeat(
      @PathVariable Long shopId,
      @RequestBody SeatRequest seatRequest) {
    return shopService.addSeat(shopId, seatRequest);
  }

  /**
   * Assign an APPROVED barber (via association) to a seat.
   */
  @PostMapping("/{shopId}/seats/{seatId}/assign")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse assignBarber(
      @PathVariable Long shopId,
      @PathVariable Long seatId,
      @RequestBody AssignBarberRequest request) {
    return shopService.assignSeatToBarber(shopId, seatId, request);
  }
}
