import { redirect } from "next/navigation";
import { getAuthUser } from "@/lib/auth";
import { CsrfSeeder } from "@/components/CsrfSeeder";
import { LogoutButton } from "./logout-button";

export default async function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const user = await getAuthUser();

  if (!user) {
    redirect("/login");
  }

  return (
    <div className="min-h-screen" data-theme="dark">
      <CsrfSeeder />
      <header className="flex items-center justify-between border-b border-[var(--color-border)] px-8 py-4">
        <h1 className="font-display text-xl font-semibold tracking-tight text-[var(--color-fg)]">
          Barber<span className="text-[var(--color-accent)]">OS</span>
        </h1>
        <div className="flex items-center gap-6">
          <span className="text-sm text-[var(--color-muted)]">
            {user.email}
          </span>
          <LogoutButton />
        </div>
      </header>
      <main className="mx-auto max-w-4xl px-8 py-12">{children}</main>
    </div>
  );
}
