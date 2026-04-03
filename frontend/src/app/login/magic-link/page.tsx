"use client";

import { useState } from "react";
import Link from "next/link";
import { fetchAPI } from "@/lib/api";

export default function MagicLinkPage() {
  const [email, setEmail] = useState("");
  const [sent, setSent] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      await fetchAPI("/api/auth/magic-link/request", {
        method: "POST",
        body: JSON.stringify({ email }),
      });
      setSent(true);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Something went wrong");
    } finally {
      setLoading(false);
    }
  }

  if (sent) {
    return (
      <div className="flex min-h-screen flex-col items-center justify-center px-4">
        <div className="w-full max-w-sm text-center">
          <div className="mx-auto mb-6 flex h-16 w-16 items-center justify-center rounded-full border border-[var(--color-accent)]">
            <svg
              className="h-8 w-8 text-[var(--color-accent)]"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              strokeWidth={1.5}
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M21.75 6.75v10.5a2.25 2.25 0 01-2.25 2.25h-15a2.25 2.25 0 01-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0019.5 4.5h-15a2.25 2.25 0 00-2.25 2.25m19.5 0v.243a2.25 2.25 0 01-1.07 1.916l-7.5 4.615a2.25 2.25 0 01-2.36 0L3.32 8.91a2.25 2.25 0 01-1.07-1.916V6.75"
              />
            </svg>
          </div>
          <h2 className="font-display text-2xl font-medium text-[var(--color-fg)]">
            Check your email
          </h2>
          <p className="mt-3 text-sm leading-relaxed text-[var(--color-muted)]">
            We sent a sign-in link to{" "}
            <span className="text-[var(--color-fg)]">{email}</span>. It expires
            in 15 minutes.
          </p>
          <Link
            href="/login"
            className="mt-8 inline-block text-sm text-[var(--color-accent)] hover:underline"
          >
            Back to sign in
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="flex min-h-screen flex-col items-center justify-center px-4">
      <div className="w-full max-w-sm">
        <div className="mb-12 text-center">
          <h1 className="font-display text-3xl font-semibold tracking-tight text-[var(--color-fg)]">
            Sign in with email
          </h1>
          <p className="mt-3 text-sm text-[var(--color-muted)]">
            We&apos;ll send you a magic link
          </p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="email" className="sr-only">
              Email address
            </label>
            <input
              id="email"
              type="email"
              required
              autoFocus
              autoComplete="email"
              placeholder="your@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full rounded-sm border border-[var(--color-border)] bg-[var(--color-surface)] px-4 py-3 text-sm text-[var(--color-fg)] placeholder-[var(--color-muted)] outline-none transition-colors focus:border-[var(--color-accent)]"
            />
          </div>

          {error && (
            <p role="alert" className="text-sm text-[var(--color-error)]">
              {error}
            </p>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full rounded-sm bg-[var(--color-accent)] px-4 py-3 text-sm font-medium text-[var(--color-obsidian)] transition-all hover:brightness-110 disabled:opacity-50"
          >
            {loading ? "Sending..." : "Send magic link"}
          </button>
        </form>

        <Link
          href="/login"
          className="mt-6 block text-center text-sm text-[var(--color-muted)] hover:text-[var(--color-fg)]"
        >
          Back to sign in
        </Link>
      </div>
    </div>
  );
}
