"use client";

import { useQuery } from "@tanstack/react-query";
import { useParams, useRouter } from "next/navigation"; // Add useRouter
import { shopServiceImpl } from "@/modules/shop/services";
import type { ShopResponse } from "@/modules/shop/types/shop.types";

export default function ShopDetailPage() {
  const router = useRouter();
  const params = useParams();
  const shopId = params.shopId as string;

  const {
    data: shop,
    isLoading,
    isError,
  } = useQuery<ShopResponse>({
    queryKey: ["shop", shopId],
    queryFn: () => shopServiceImpl.getShop(shopId),
    enabled: !!shopId,
  });

  if (isLoading) return <div>Loading shop details...</div>;
  if (isError || !shop) return <div>Failed to load shop</div>;

  return (
    <div className="p-6">
      <div className="flex justify-between items-start mb-4">
        <h1 className="text-3xl font-bold">{shop.name}</h1>
        <button
          onClick={() => router.push(`/shops/${shopId}/update`)}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Edit Shop
        </button>
      </div>

      <p className="mb-4">{shop.address}</p>

      {shop.seats && shop.seats.length > 0 && (
        <div className="mt-4">
          <h2 className="text-xl font-semibold mb-2">Seats</h2>
          <ul className="space-y-2">
            {shop.seats.map((seat) => (
              <li key={seat.id} className="border p-2 rounded">
                {seat.seatName} - {seat.barberFullName || "Unassigned"}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}
