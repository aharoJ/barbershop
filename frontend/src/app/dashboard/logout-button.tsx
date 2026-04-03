"use client";

import { fetchAPI } from "@/lib/api";

export function LogoutButton() {
  async function handleLogout() {
    try {
      await fetchAPI("/api/auth/logout", { method: "POST" });
    } catch {
      // logout might fail if session expired — still redirect
    }
    window.location.href = "/login";
  }

  return (
    <button
      onClick={handleLogout}
      className="text-sm text-[var(--color-muted)] transition-colors hover:text-[var(--color-fg)]"
    >
      Sign out
    </button>
  );
}
