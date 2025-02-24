"use client";
// app/owners/[userId]/page.tx
import { useQuery } from "@tanstack/react-query";
import { ownerServiceImpl } from "@/modules/owner/services";
import { useParams, useRouter } from "next/navigation";
import { useAuthStore } from "@/stores/auth.store";
import { OwnerResponse } from "@/modules/owner/types/owner.types";
import { ShopResponse } from "@/modules/shop/types/shop.types";
import { shopServiceImpl } from "@/modules/shop/services";

export default function OwnerDashboard() {
  const params = useParams();
  const userId = params.userId as string; // Casting to a single string
  const router = useRouter();
  const { user } = useAuthStore();

  // Basic security check (optional):
  if (user && user.id !== userId) {
    router.push("/unauthorized");
  }

  // 1) Fetch the owner profile
  const {
    data: ownerProfile,
    isLoading,
    isError,
  } = useQuery<OwnerResponse>({
    queryKey: ["owner-profile", userId],
    queryFn: () => ownerServiceImpl.getOwnerProfile(userId),
    enabled: !!userId, // only run if userId is defined
  });

  // 2) Fetch shops for this owner
  const {
    data: myShops,
    isLoading: isLoadingShops,
    isError: isErrorShops,
  } = useQuery<ShopResponse[]>({
    queryKey: ["my-shops", userId],
    queryFn: () => shopServiceImpl.getShopsByOwner(userId), // your new endpoint
    enabled: !!userId,
  });


  if (isLoading || isLoadingShops) return <div>Loading...</div>;
  if (isError || !ownerProfile) return <div>Failed to load owner profile</div>;
  if (isErrorShops || !myShops ) return <div>Failed to load shops</div>;

  const handleEdit = () => {
    router.push(`/owners/${userId}/profile/edit`);
  };

  // or ??? => router.push(`/owners/${userId}/shops/create`);
  const handleCreateShop = () => router.push(`/shops/create`);
  
  const handleUpdateShop = () => router.push(`/shops/edit`);

  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-4">Owner Dashboard</h1>
      <div className="bg-white p-6 rounded-md shadow-md w-[600px]">
        <h2 className="text-2xl mb-2">Profile Info</h2>
        <p>
          <strong>Full Name:</strong> {ownerProfile.firstName}{" "}
          {ownerProfile.lastName}
        </p>
        <p>
          <strong>Email:</strong> {ownerProfile.email}
        </p>
        <p>
          <strong>Phone:</strong> {ownerProfile.phoneNumber}
        </p>
        <div className="flex flex-col">
          <button
            onClick={handleEdit}
            className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
          >
            Edit Profile
          </button>
          <button
            onClick={handleUpdateShop}
            className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
          >
            UPDATE SHOP
          </button>
          <button
            onClick={handleCreateShop}
            className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
          >
            CREATE SHOP
          </button>
        </div>
      </div>
    </div>
  );
}

