// @/modules/image/services/image.service.ts

import { ImageResponse } from "../types/image.types";

export interface ImageService {
  /**
   * Overwrite the single image for an owner
   */
  uploadOwnerImage(ownerId: number, file: File): Promise<ImageResponse>;

  /**
   * Get the single image for an owner
   */
  getOwnerImage(ownerId: number): Promise<ImageResponse | null>;

  uploadBarberImage(barberId: number, file: File): Promise<ImageResponse>;
  uploadCustomerImage(customerId: number, file: File): Promise<ImageResponse>;
  uploadShopImage(shopId: number, file: File): Promise<ImageResponse>;
}
