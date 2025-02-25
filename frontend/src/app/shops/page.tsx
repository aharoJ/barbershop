// @/app/shops/page.tsx
"use client";

import { useQuery } from "@tanstack/react-query";
import { shopService } from "@/modules/shop/services";
import type { ShopResponse } from "@/modules/shop/types/shop.types";
import Link from "next/link";

export default function ShopListPage() {
  const {
    data: shops,
    isLoading,
    isError,
  } = useQuery<ShopResponse[]>({
    queryKey: ["shops"],
    queryFn: () => shopService.listShops(),
  });

  if (isLoading) return <div className="p-6 text-center">Loading shops...</div>;
  if (isError || !shops)
    return (
      <div className="p-6 text-center text-red-500">Failed to load shops</div>
    );

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">All Shops</h1>
      <ul className="space-y-4">
        {shops.map((shop) => (
          <li
            key={shop.id}
            className="bg-white shadow-md hover:shadow-xl transition-shadow duration-200 p-4 rounded"
          >
            <Link href={`/shops/${shop.id}`} className="block">
              <span className="text-xl font-semibold text-gray-800">
                {shop.name}
              </span>
              <p className="text-sm text-gray-500">{shop.address}</p>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}
