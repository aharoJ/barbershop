package io.aharoj.barbershop_backend.modules.shop.service;

import java.util.List;

import io.aharoj.barbershop_backend.modules.shop.dto.request.AssignBarberRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.request.SeatRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.request.ShopRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.response.SeatResponse;
import io.aharoj.barbershop_backend.modules.shop.dto.response.ShopResponse;

public interface ShopService {

  /**
   * Creates a new shop.
   *
   * @param ownerId the ID of the owner creating the shop
   * @param request the data required to create the shop
   * @return a ShopResponse containing the newly created shop details
   * @sidenote This method encapsulates the creation logic and validation rules
   *           required to establish a new shop.
   */
  ShopResponse createShop(Long ownerId, ShopRequest request);

  /**
   * Retrieves a shop by its unique identifier.
   *
   * @param shopId the ID of the shop to retrieve
   * @return a ShopResponse containing the shop's details
   * @sidenote This method ensures that the shop details are fetched while keeping
   *           the underlying entity secure.
   */
  ShopResponse getShopById(Long shopId);

  /**
   * Retrieves a list of all available shops.
   *
   * @return a list of ShopResponse objects representing all shops
   * @sidenote This method provides public access to shop information, allowing
   *           barbers to discover available shops.
   */
  List<ShopResponse> getAllShops();

  /**
   * Adds a new seat to a shop.
   *
   * @param shopId  the ID of the shop to which a seat is being added
   * @param request the data required to create the new seat
   * @return a SeatResponse containing details of the newly added seat
   * @sidenote This method manages seat configurations within a shop, facilitating
   *           the expansion of seating capacity.
   */
  SeatResponse addSeat(Long shopId, SeatRequest request);

  /**
   * Assigns a seat to an existing barber association.
   *
   * @param shopId  the ID of the shop containing the seat
   * @param seatId  the ID of the seat to be assigned
   * @param request the details required to assign the seat to a barber
   * @return a SeatResponse reflecting the updated seat assignment
   * @sidenote This method ensures a proper association between a barber and a
   *           designated seat within the shop.
   */
  SeatResponse assignSeatToBarber(Long shopId, Long seatId, AssignBarberRequest request);
}
