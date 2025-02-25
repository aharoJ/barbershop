import type { OwnerPayload, OwnerResponse } from "../types/owner.types";

/**
 * This interface provides a contract for any Owner service implementation.
 * We can create mocks or real services that match these methods.
 */
export interface OwnerService {
  createOwnerProfile(payload: OwnerPayload): Promise<OwnerResponse>;
  getOwnerProfile(): Promise<OwnerResponse>;
  updateOwnerProfile(payload: OwnerPayload): Promise<OwnerResponse>;
  //   deleteOwnerProfile(userId: string): Promise<void>; -- not created yet
}
