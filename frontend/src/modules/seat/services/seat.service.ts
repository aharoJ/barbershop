// @/modules/seat/services/seat.service.ts
import type { z } from "zod";
import { seatSchema, SeatResponse } from "../types/seat.types";

export interface SeatService {
  /**
   * Adds a new seat for the specified shop.
   */
  addSeat(
    shopId: string,
    payload: z.infer<typeof seatSchema>,
  ): Promise<SeatResponse>;

  /**
   * Retrieves all seats for a given shop.
   */
  getSeatsByShop(shopId: string): Promise<SeatResponse[]>;

  /**
   * Retrieves a specific seat by its ID for a given shop.
   */
  getSeatById(shopId: string, seatId: string): Promise<SeatResponse>;

  /**
   * Updates the seat information for a specific seat.
   *
   * In this example, we use a PUT request which is more conventional for updates.
   */
  updateSeatInfo(
    shopId: string,
    seatId: string,
    payload: z.infer<typeof seatSchema>,
  ): Promise<SeatResponse>;

  /**
   * Removes a seat from the shop.
   *
   * Note: This calls DELETE on a slightly different endpoint as per the backend.
   */
  removeSeat(shopId: string, seatId: string): Promise<SeatResponse>;

  /**
   * Assigns a seat to a barber using the provided association ID.
   */
  assignSeatToBarber(
    shopId: string,
    seatId: string,
    barberAssociationId: number,
  ): Promise<SeatResponse>;
}
