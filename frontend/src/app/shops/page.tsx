// @/app/shops/page.tsx
"use client";

import { useQuery } from "@tanstack/react-query";
import { shopServiceImpl } from "@/modules/shop/services";
import type { ShopResponse } from "@/modules/shop/types/shop.types";
import Link from "next/link";

export default function ShopListPage() {
  const {
    data: shops,
    isLoading,
    isError,
  } = useQuery<ShopResponse[]>({
    queryKey: ["shops"],
    queryFn: () => shopServiceImpl.listShops(),
  });

  if (isLoading) return <div>Loading shops...</div>;
  if (isError || !shops) return <div>Failed to load shops</div>;

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-4">All Shops</h1>
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
    </div>
  );
}
