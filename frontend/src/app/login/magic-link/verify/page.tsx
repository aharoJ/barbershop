"use client";

import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import Link from "next/link";
import { fetchAPI } from "@/lib/api";

export default function VerifyMagicLinkPage() {
  const searchParams = useSearchParams();
  const token = searchParams.get("token");

  const [status, setStatus] = useState<"verifying" | "success" | "error">(
    "verifying",
  );
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!token) {
      setStatus("error");
      setError("Missing token");
      return;
    }

    let cancelled = false;

    async function verify() {
      try {
        const result = await fetchAPI<{ redirectUrl: string }>(
          "/api/auth/magic-link/verify",
          {
            method: "POST",
            body: JSON.stringify({ token }),
          },
        );

        if (!cancelled) {
          setStatus("success");
          window.location.href = result.redirectUrl;
        }
      } catch (err) {
        if (!cancelled) {
          setStatus("error");
          setError(
            err instanceof Error ? err.message : "Verification failed",
          );
        }
      }
    }

    verify();

    return () => {
      cancelled = true;
    };
  }, [token]);

  if (status === "error") {
    return (
      <div className="flex min-h-screen flex-col items-center justify-center px-4">
        <div className="w-full max-w-sm text-center">
          <div className="mx-auto mb-6 flex h-16 w-16 items-center justify-center rounded-full border border-[var(--color-error)]">
            <svg
              className="h-8 w-8 text-[var(--color-error)]"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              strokeWidth={1.5}
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </div>
          <h2 className="font-display text-2xl font-medium text-[var(--color-fg)]">
            Link expired
          </h2>
          <p className="mt-3 text-sm leading-relaxed text-[var(--color-muted)]">
            {error || "This magic link is invalid or has already been used."}
          </p>
          <Link
            href="/login/magic-link"
            className="mt-8 inline-block rounded-sm bg-[var(--color-accent)] px-6 py-2.5 text-sm font-medium text-[var(--color-obsidian)] transition-all hover:brightness-110"
          >
            Request a new link
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="flex min-h-screen flex-col items-center justify-center px-4">
      <div className="w-full max-w-sm text-center">
        <div className="mx-auto mb-6 h-8 w-8 animate-spin rounded-full border-2 border-[var(--color-border)] border-t-[var(--color-accent)]" />
        <h2 className="font-display text-2xl font-medium text-[var(--color-fg)]">
          Signing you in
        </h2>
        <p className="mt-3 text-sm text-[var(--color-muted)]">
          Verifying your magic link...
        </p>
      </div>
    </div>
  );
}
