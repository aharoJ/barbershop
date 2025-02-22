// @components/auth/ProtectedRoute.tsx -- AUTH
"use client";

import { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import { useAuthHydration, useAuthStore } from "@/stores/auth.store";

export function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const [loading, setLoading] = useState(true);
  const { user, token } = useAuthStore();
  const router = useRouter();
  const pathname = usePathname();

  // hydrate it
  const hydrated = useAuthHydration();

  useEffect(() => {
    const validateAccess = async () => {
      if (!hydrated) return;

      setLoading(true);

      // Immediate auth check
      if (!user || !token) {
        router.push(`/auth/login?redirect=${encodeURIComponent(pathname)}`);
        return;
      }
    };

    validateAccess();
  }, [hydrated, user, token, pathname, router]);

  if (!hydrated) return <div>Loading...</div>;

  if (loading) return <div>Loading...</div>;
  return <>{children}</>;
}
