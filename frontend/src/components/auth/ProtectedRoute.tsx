// @components/auth/ProtectedRoute.tsx

"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { useAuthStore } from "@/stores/auth.store";
import { customerService } from "@/services/customer.service";

export function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { user, token } = useAuthStore();
  const router = useRouter();

  useEffect(() => {
    const checkProfile = async () => {
      if (!user || !token) {
        router.push("/login");
        return;
      }

      try {
        // Use corrected endpoint
        await customerService.getCustomerProfile();
      } catch (error) {
        console.error("Profile check failed:", error);
        router.push("/profile/create");
      }
    };

    checkProfile();
  }, [user, token, router]);

  return <>{children}</>;
}
