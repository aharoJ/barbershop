// @/modules/shop/services/IShopService.ts
import type { ShopPayload, ShopResponse } from "../types/shop.types";
import type { z } from "zod";
import type { seatSchema, SeatResponse } from "../types/seat.types";

/**
 * The interface describing the methods needed for "Shop" logic.
 *
 * Adjust based on how your backend is structured
 * (e.g. if you do create, list, read, update, add seat, etc.)
 */
export interface ShopService {
  listShops(): Promise<ShopResponse[]>; // GET /api/shops
  createShop(payload: ShopPayload): Promise<ShopResponse>; // POST /api/shops
  updateShop(shopId: string, payload: ShopPayload): Promise<ShopResponse>; // POST /api/shops
  // updateShop(payload: ShopPayload): Promise<ShopResponse>; // POST /api/shops
  getShop(shopId: string): Promise<ShopResponse>; // GET /api/shops/{shopId} -- OPEN TO ALL

  // Seat-related actions:
  addSeat(
    shopId: string,
    payload: z.infer<typeof seatSchema>,
  ): Promise<SeatResponse>;
  assignSeatToBarber(
    shopId: string,
    seatId: string,
    barberAssociationId: number,
  ): Promise<SeatResponse>;

  // return all shops owned by the owner
  getShopsByOwner(): Promise<ShopResponse[]>;
}
