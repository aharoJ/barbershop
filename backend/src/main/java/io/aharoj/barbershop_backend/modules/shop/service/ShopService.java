package io.aharoj.barbershop_backend.modules.shop.service;

import io.aharoj.barbershop_backend.modules.shop.dto.request.AssignBarberRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.request.SeatRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.request.ShopRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.response.SeatResponse;
import io.aharoj.barbershop_backend.modules.shop.dto.response.ShopResponse;

public interface ShopService {

  /**
   * Create shop with the given owner profile and shop request.
   *
   * @param ownerProfileId the ID of the owner profile
   * @param request        the details for creating a new shop
   * @return a ShopResponse containing the created shop details
   * @note for client
   */
  ShopResponse createShop(Long ownerProfileId, ShopRequest request);

  /**
   * Add a new seat to the specified shop.
   *
   * @param shopId  the ID of the shop
   * @param request the details of the seat to be added
   * @return a SeatResponse containing the details of the newly added seat
   * @note for client
   */
  SeatResponse addSeatToShop(Long shopId, SeatRequest request);

  /**
   * Assign a barber to a specific seat in a shop.
   *
   * @param shopId  the ID of the shop
   * @param seatId  the ID of the seat where the barber will be assigned
   * @param request the details of the barber assignment
   * @return a SeatResponse containing the updated seat details with the assigned
   *         barber
   * @note for client
   */
  SeatResponse assignBarberToSeat(Long shopId, Long seatId, AssignBarberRequest request);

  /**
   * Retrieve details of a shop by its ID.
   *
   * @param shopId the ID of the shop to be retrieved
   * @return a ShopResponse containing the shop details
   * @note for client
   */
  ShopResponse getShop(Long shopId);
}
