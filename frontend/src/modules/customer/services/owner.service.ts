import { CustomerPayload, CustomerResponse } from "../types/customer.types";

export interface CustomerService {
  createCustomerProfile(payload: CustomerPayload): Promise<CustomerResponse>;
  getCustomerProfile(userId: string): Promise<CustomerResponse>;
  updateCustomerProfile(userId: string, payload: CustomerPayload): Promise<CustomerResponse>;
}