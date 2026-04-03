import { getCsrfToken } from "./csrf";

class ApiError extends Error {
  constructor(
    public status: number,
    message: string,
  ) {
    super(message);
    this.name = "ApiError";
  }
}

const MUTATION_METHODS = new Set(["POST", "PUT", "PATCH", "DELETE"]);

async function seedCsrf(): Promise<void> {
  await fetch("/api/auth/me", { credentials: "include" });
}

export async function fetchAPI<T>(
  path: string,
  options: RequestInit = {},
): Promise<T> {
  const headers: Record<string, string> = {
    "Content-Type": "application/json",
    Accept: "application/json",
    ...(options.headers as Record<string, string>),
  };

  const method = (options.method || "GET").toUpperCase();

  if (MUTATION_METHODS.has(method)) {
    const token = getCsrfToken();
    if (token) {
      headers["X-XSRF-TOKEN"] = token;
    }
  }

  let response = await fetch(path, {
    ...options,
    headers,
    credentials: "include",
  });

  if (response.status === 403 && MUTATION_METHODS.has(method)) {
    await seedCsrf();
    const retryToken = getCsrfToken();
    if (retryToken) {
      headers["X-XSRF-TOKEN"] = retryToken;
    }
    response = await fetch(path, {
      ...options,
      headers,
      credentials: "include",
    });
  }

  if (response.status === 204) {
    return undefined as T;
  }

  if (!response.ok) {
    let message = `Request failed (${response.status})`;
    try {
      const body = await response.json();
      message = body.detail || body.message || body.error || message;
    } catch {
      // response body not JSON
    }
    throw new ApiError(response.status, message);
  }

  return response.json();
}
