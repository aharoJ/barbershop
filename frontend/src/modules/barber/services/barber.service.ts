import { BarberPayload, BarberResponse } from "../types/barber.types";

/**
 * This interface provides a contract for any Babrer service implementation.
 * We can create mocks or real services that match these methods.
 */
export interface BarberService {
  createBarberProfile(payload: BarberPayload): Promise<BarberResponse>;
  getBarberProfile(): Promise<BarberResponse>;
  updateBarberProfile(payload: BarberPayload): Promise<BarberResponse>;
}
