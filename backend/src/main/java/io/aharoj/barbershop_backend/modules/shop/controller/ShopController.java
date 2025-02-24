package io.aharoj.barbershop_backend.modules.shop.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.aharoj.barbershop_backend.modules.auth.serviceImpl.UserDetailsImpl;
import io.aharoj.barbershop_backend.modules.shop.dto.request.*;
import io.aharoj.barbershop_backend.modules.shop.dto.response.*;
import io.aharoj.barbershop_backend.modules.shop.service.ShopService;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

  private final ShopService shopService;

  public ShopController(ShopService shopService) {
    this.shopService = shopService;
  }

  // @PostMapping("/owner/create") -- I THINK WE CAN WE AWAY WITH NOT DOING THIS
  @PostMapping()
  @PreAuthorize("hasRole('OWNER')")
  public ShopResponse createShop(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ShopRequest request) {
    return shopService.createShop(userDetails.getId(), request);
  }

  @GetMapping
  public List<ShopResponse> listShops() {
    return shopService.getAllShops();
  }

  @GetMapping("/{shopId}")
  public ShopResponse getShopDetails(@PathVariable Long shopId) {
    return shopService.getShopById(shopId);
  }

  // missing update
  @PutMapping("/{shopId}")
  public ShopResponse updateShopInformation(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long shopId,
      @RequestBody ShopRequest request) {
    return shopService.updateShop(userDetails.getId(), shopId, request);
  }

  // -------------------- SEAT BELOW --------------

  @PostMapping("/{shopId}/seats")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse addSeat(
      @PathVariable Long shopId,
      @RequestBody SeatRequest seatRequest) {
    return shopService.addSeat(shopId, seatRequest);
  }

  @PostMapping("/{shopId}/seats/{seatId}/assign")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse assignBarber(
      @PathVariable Long shopId,
      @PathVariable Long seatId,
      @RequestBody AssignBarberRequest request) {
    return shopService.assignSeatToBarber(shopId, seatId, request);
  }

  // -------------------- SEAT BELOW --------------
  // ion know why I did this one?
  @GetMapping("/owner/{ownerId}")
  @PreAuthorize("hasRole('OWNER')")
  public List<ShopResponse> getShopsByOwner(@PathVariable Long ownerId) {
    return shopService.getShopsByOwner(ownerId);
  }
}
