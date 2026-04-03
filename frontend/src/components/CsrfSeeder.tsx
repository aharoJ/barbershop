"use client";

import { useEffect } from "react";

export function CsrfSeeder() {
  useEffect(() => {
    fetch("/api/auth/me", { credentials: "include" }).catch(() => {});
  }, []);

  return null;
}
