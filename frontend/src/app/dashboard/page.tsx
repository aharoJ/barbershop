import { getAuthUser } from "@/lib/auth";

export default async function DashboardPage() {
  const user = await getAuthUser();

  return (
    <div>
      <h2 className="font-display text-3xl font-medium text-[var(--color-fg)]">
        Welcome back
        {user?.name ? (
          <>
            , <span className="text-[var(--color-accent)]">{user.name}</span>
          </>
        ) : null}
      </h2>
      <p className="mt-4 text-[var(--color-muted)]">
        Your dashboard is ready. Modules will appear here as they&apos;re built.
      </p>
    </div>
  );
}
