// @services/customer.service.ts

import { apiClient } from "@/utils/api-client";
import type { CustomerPayload, CustomerResponse } from "@/types/customer.types";

export const customerService = {
  createCustomer: async (payload: CustomerPayload) => {
    const { data } = await apiClient.post<CustomerResponse>(
      "/api/customers/profile", // CORRECTED ENDPOINT
      {
        ...payload,
        // Convert Date to ISO string
        dateOfBirth: payload.dateOfBirth.toISOString(),
      },
    );
    return data;
  },

  // should I remove this?
  getCustomers: async () => {
    const { data } = await apiClient.get<CustomerResponse[]>("/api/customers");
    return data;
  },

  getCustomerProfile: async () => {
    const { data } = await apiClient.get<CustomerResponse>(
      "/api/customers/profile", // Corrected endpoint
    );
    return data;
  },
};
