// @app/(customer)/dashboard/page.tsx

"use client";

"use client";

import { useQuery } from "@tanstack/react-query";
import { customerService } from "@/services/customer.service";
import { useAuthStore } from "@/stores/auth.store";

export default function CustomerDashboard() {
  const { user } = useAuthStore();
  const { data: profile } = useQuery({
    queryKey: ["customer-profile"],
    queryFn: customerService.getCustomerProfile,
  });

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">
        Welcome, {profile?.firstName} {profile?.lastName}
      </h1>

      <div className="bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-xl font-semibold mb-4">Your Profile</h2>
        <p className="mb-2">
          <strong>Email:</strong> {profile?.email}
        </p>
        <p className="mb-2">
          <strong>Role:</strong> {user?.roles.join(", ")}
        </p>
      </div>
    </div>
  );
}
