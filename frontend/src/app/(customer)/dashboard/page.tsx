// @app/(customer)/dashboard/page.tsx

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
      <h1 className="text-5xl font-bold mb-8 text-black/60">
        Welcome, {profile?.firstName} {profile?.lastName}
      </h1>

      <div className="p-8 rounded-xl shadow-2xl mx-52 bg-slate-200 text-center">
        <h2 className="text-3xl font-bold mb-6 text-black/60 border-b-2 pb-4">
          Profile Details
        </h2>

        <div className="space-y-3 mx-auto ">
          <div className="flex items-center gap-4">
            <span className="text-lg  text-black/60 w-auto">First Name:</span>
            <span className="text-lg tracking-wider font-mono text-black/70">
              {profile?.firstName}
            </span>
          </div>

          <div className="flex items-center gap-4">
            <span className="text-lg  text-black/60 w-auto">Last Name:</span>
            <span className="text-lg tracking-wider font-mono text-black/70">
              {profile?.lastName}
            </span>
          </div>

          <div className="flex items-center gap-4">
            <span className="text-lg  text-black/60 w-auto">Phone:</span>
            <span className="text-lg tracking-wider font-mono text-black/70">
              {profile?.phoneNumber}
            </span>
          </div>

          <div className="flex items-center gap-4">
            <span className="text-lg  text-black/60 w-auto">Role:</span>
            <span className="text-lg tracking-wider font-mono text-black/70">
              {user?.roles.join(", ")}
            </span>
          </div>

          <div className="flex items-center gap-4">
            <span className="text-lg text-black/60 w-auto">Date of Birth:</span>
            <span className="text-lg tracking-wider font-mono text-black/70">
              {profile?.dateOfBirth?.toLocaleDateString()}
            </span>
          </div>

          <div className="flex items-center gap-4">
            <span className="text-lg text-black/60 w-auto">Gender:</span>
            <span className="text-lg tracking-wider font-mono text-black/70">
              {profile?.gender?.toLowerCase()}
            </span>
          </div>

          <div className="flex items-center gap-4">
            <span className="text-lg text-black/60 w-auto">Notifications:</span>
            <span className="text-lg tracking-wider font-mono text-black/70">
              {profile?.marketingNotifications?.toLowerCase()}
            </span>
          </div>

          <div className="mt-6 p-4 bg-white rounded-lg shadow">
            <h3 className="text-lg font-semibold mb-2">Address</h3>
            <div className="space-y-1">
              <p>{profile?.address?.street}</p>
              <p>
                {profile?.address?.city}, {profile?.address?.state}{" "}
                {profile?.address?.postalCode}
              </p>
              <p>{profile?.address?.country}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
