import Link from "next/link";

export default function LandingPage() {
  return (
    <div className="flex min-h-screen flex-col">
      <header className="flex items-center justify-between px-8 py-6">
        <h1 className="font-display text-2xl font-semibold tracking-tight text-[var(--color-fg)]">
          Barber<span className="text-[var(--color-accent)]">OS</span>
        </h1>
        <Link
          href="/login"
          className="rounded-sm border border-[var(--color-accent)] px-6 py-2.5 text-sm font-medium tracking-wide text-[var(--color-accent)] transition-all hover:bg-[var(--color-accent)] hover:text-[var(--color-obsidian)]"
        >
          Sign in
        </Link>
      </header>

      <main className="flex flex-1 flex-col items-center justify-center px-8">
        <div className="mx-auto max-w-2xl text-center">
          <p className="mb-4 text-xs font-medium uppercase tracking-[0.2em] text-[var(--color-accent)]">
            Open Source
          </p>
          <h2 className="font-display text-5xl font-medium leading-tight tracking-tight text-[var(--color-fg)] md:text-7xl">
            The craft deserves
            <br />
            <span className="text-[var(--color-accent)]">better tools</span>
          </h2>
          <p className="mx-auto mt-8 max-w-lg text-lg leading-relaxed text-[var(--color-muted)]">
            Discover exceptional barbers. Book with confidence. Build your
            reputation. The open-source platform for barbershops that take their
            craft seriously.
          </p>
          <div className="mt-12 flex items-center justify-center gap-6">
            <Link
              href="/login"
              className="rounded-sm bg-[var(--color-accent)] px-8 py-3 text-sm font-medium tracking-wide text-[var(--color-obsidian)] transition-all hover:brightness-110"
            >
              Get started
            </Link>
            <a
              href="https://github.com/aharoJ/barbershop"
              target="_blank"
              rel="noopener noreferrer"
              className="text-sm font-medium text-[var(--color-muted)] transition-colors hover:text-[var(--color-fg)]"
            >
              View on GitHub
            </a>
          </div>
        </div>
      </main>

      <footer className="px-8 py-8 text-center text-xs text-[var(--color-muted)]">
        AGPL-3.0 &middot; Built for barbers, by barbers.
      </footer>
    </div>
  );
}
