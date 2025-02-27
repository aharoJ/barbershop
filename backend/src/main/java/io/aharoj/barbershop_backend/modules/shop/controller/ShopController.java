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

  // HOW DO I GET SHOPS BY OWNER?
  @GetMapping("/owned")
  @PreAuthorize("hasRole('OWNER')")
  public List<ShopResponse> getOwnedShops(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    return shopService.getShopsByOwner(userDetails.getId());
  }

}
