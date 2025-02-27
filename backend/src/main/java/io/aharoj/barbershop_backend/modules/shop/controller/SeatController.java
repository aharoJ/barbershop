package io.aharoj.barbershop_backend.modules.shop.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.aharoj.barbershop_backend.modules.shop.dto.request.*;
import io.aharoj.barbershop_backend.modules.shop.dto.response.*;
import io.aharoj.barbershop_backend.modules.shop.service.SeatService;

@RestController
@RequestMapping("/api/shops/{shopId}")
public class SeatController {
  private final SeatService seatService;

  public SeatController(SeatService seatService) {
    this.seatService = seatService;
  }

  @PostMapping("/seats")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse createSeat(
      @PathVariable Long shopId,
      @RequestBody SeatRequest seatRequest) {
    return seatService.createSeat(shopId, seatRequest);
  }

  @GetMapping("/seats")
  // @PreAuthorize("hasRole('OWNER')") -- UNSURE IF THIS CAN BE PUBLIC OR NOT
  public List<SeatResponse> getAllSeatsByShop(@PathVariable Long shopId) {
    return seatService.getSeatsByShop(shopId);
  }

  @GetMapping("/seats/{seatId}")
  // @PreAuthorize("hasRole('OWNER')") -- UNSURE IF THIS CAN BE PUBLIC OR NOT
  public SeatResponse getSeatById(@PathVariable Long shopId, @PathVariable Long seatId) {
    return seatService.getSeatById(shopId, seatId);
  }

  @PutMapping("/seats/{seatId}")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse updateSeatInfo(@PathVariable Long  shopId, @PathVariable Long seatId, @RequestBody SeatRequest request) {
    return seatService.updateSeatById(shopId, seatId, request);
  }

  @DeleteMapping("/{seatId}")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse removeSeat(@PathVariable Long shopId,
      @PathVariable Long seatId) {
    return seatService.removeSeatById(shopId, seatId);
  }

  // UNSURE IF I SHUD KEEP BELOW LOGIC
  @PostMapping("/seats/{seatId}/assign")
  @PreAuthorize("hasRole('OWNER')")
  public SeatResponse assignBarber(
      @PathVariable Long shopId,
      @PathVariable Long seatId,
      @RequestBody AssignBarberRequest request) {
    return seatService.assignSeatToBarber(shopId, seatId, request);
  }

}
