// @/app/my-shops/page.tsx
"use client";

import { useQuery } from "@tanstack/react-query";
import { shopService } from "@/modules/shop/services";
import type { ShopResponse } from "@/modules/shop/types/shop.types";
import Link from "next/link";

export default function MyShopsPage() {
  const {
    data: shops,
    isLoading,
    isError,
  } = useQuery<ShopResponse[]>({
    queryKey: ["ownedShops"],
    queryFn: () => shopService.getShopsByOwner(),
  });

  if (isLoading) return <div>Loading your shops...</div>;
  if (isError || !shops) return <div>Failed to load your shops</div>;

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-3xl font-bold">Your Shops</h1>
        <Link
          href="/shops/create"
          className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          Create New Shop
        </Link>
      </div>

      {shops.length === 0 ? (
        <div className="text-gray-500">No shops found</div>
      ) : (
        <ul className="space-y-3">
          {shops.map((shop) => (
            <li key={shop.id} className="bg-white shadow p-3 rounded">
              <Link href={`/shops/${shop.id}`}>
                <span className="text-xl font-semibold">{shop.name}</span>
                <p className="text-sm">{shop.address}</p>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
