import { cookies } from "next/headers";

const API_URL = process.env.API_URL || "http://localhost:8080";

class AuthRequiredError extends Error {
  constructor() {
    super("Authentication required");
    this.name = "AuthRequiredError";
  }
}

export async function fetchAPIServer<T>(path: string): Promise<T> {
  const cookieStore = await cookies();
  const cookieHeader = cookieStore
    .getAll()
    .map((c) => `${c.name}=${c.value}`)
    .join("; ");

  const response = await fetch(`${API_URL}${path}`, {
    headers: {
      Cookie: cookieHeader,
      Accept: "application/json",
    },
    cache: "no-store",
  });

  if (response.status === 401) {
    throw new AuthRequiredError();
  }

  if (!response.ok) {
    let message = `Server request failed (${response.status})`;
    try {
      const body = await response.json();
      message = body.detail || body.message || body.error || message;
    } catch {
      // response body not JSON
    }
    throw new Error(message);
  }

  return response.json();
}

export { AuthRequiredError };
