import { CustomerPayload, CustomerResponse } from "../types/customer.types";

export interface CustomerService {
  createCustomerProfile(payload: CustomerPayload): Promise<CustomerResponse>;
  getCustomerProfile(): Promise<CustomerResponse>;
  updateCustomerProfile(payload: CustomerPayload): Promise<CustomerResponse>;
}
