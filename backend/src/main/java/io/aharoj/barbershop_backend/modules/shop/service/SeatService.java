package io.aharoj.barbershop_backend.modules.shop.service;

import java.util.List;

import io.aharoj.barbershop_backend.modules.shop.dto.request.AssignBarberRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.request.SeatRequest;
import io.aharoj.barbershop_backend.modules.shop.dto.response.SeatResponse;

public interface SeatService {
  /**
   * Adds a new seat to a shop.
   *
   * @param shopId  the ID of the shop to which a seat is being added
   * @param request the data required to create the new seat
   * @return a SeatResponse containing details of the newly added seat
   * @sidenote This method manages seat configurations within a shop, facilitating
   *           the expansion of seating capacity.
   */
  SeatResponse createSeat(Long shopId, SeatRequest request);

  // get all seats by Shop
  // List<SeatResponse> getAllSeats();
  List<SeatResponse> getSeatsByShop(Long shopId);

  // get by seatId
  SeatResponse getSeatById(Long shopId, Long seatId);

  // delete by seatId
  SeatResponse removeSeatById(Long shopId, Long seatId);

  // update seatId
  SeatResponse updateSeatById(Long shopId, Long seatId, SeatRequest request);

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
