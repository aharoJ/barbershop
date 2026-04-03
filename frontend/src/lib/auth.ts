import { cache } from "react";
import { fetchAPIServer } from "./api-server";
import type { UserInfo } from "./types";

export const getAuthUser = cache(async (): Promise<UserInfo | null> => {
  try {
    return await fetchAPIServer<UserInfo>("/api/auth/me");
  } catch {
    return null;
  }
});
