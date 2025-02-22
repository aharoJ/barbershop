"use client";
// app/owners/[userId]/page.tx
import { useQuery } from "@tanstack/react-query";
import { ownerServiceImpl } from "@/modules/owner/services";
import { useParams, useRouter } from "next/navigation";
import { useAuthStore } from "@/stores/auth.store";
import { OwnerResponse } from "@/modules/owner/types/owner.types";

export default function OwnerDashboard() {
  const params = useParams();
  const userId = params.userId as string; // Casting to a single string
  const router = useRouter();
  const { user } = useAuthStore();

  // Basic security check (optional):
  if (user && user.id !== userId) {
    router.push("/unauthorized");
  }

  const {
    data: ownerProfile,
    isLoading,
    isError,
  } = useQuery<OwnerResponse>({
    queryKey: ["owner-profile", userId],
    queryFn: () => ownerServiceImpl.getOwnerProfile(userId),
    enabled: !!userId, // only run if userId is defined
  });

  if (isLoading) return <div>Loading Owner Profile...</div>;
  if (isError || !ownerProfile) return <div>Failed to load owner profile</div>;

  const handleEdit = () => {
    router.push(`/owners/${userId}/profile/edit`);
  };

  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-4">Owner Dashboard</h1>
      <div className="bg-white p-6 rounded-md shadow-md w-[600px]">
        <h2 className="text-2xl mb-2">Profile Info</h2>
        <p>
          <strong>Full Name:</strong> {ownerProfile.firstName} {ownerProfile.lastName}
        </p>
        <p>
          <strong>Email:</strong> {ownerProfile.email}
        </p>
        <p>
          <strong>Phone:</strong> {ownerProfile.phoneNumber}
        </p>
        <button
          onClick={handleEdit}
          className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          Edit Profile
        </button>
      </div>
    </div>
  );
}

