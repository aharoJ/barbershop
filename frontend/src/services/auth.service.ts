// @services/auth.service.ts

import { apiClient } from "@/utils/api-client";
import type { LoginPayload, RegisterPayload } from "@/types/auth.types";

export const authService = {
  login: async (payload: LoginPayload) => {
    const { data } = await apiClient.post("/api/auth/login", payload);
    return data;
  },

  register: async (payload: RegisterPayload) => {
    const { data } = await apiClient.post("/api/auth/signup", {
      ...payload,
      role: payload.role ? [payload.role] : undefined,
    });
    return data;
  },

  getMe: async () => {
    const { data } = await apiClient.get("/api/auth/me");
    return data;
  },
};
