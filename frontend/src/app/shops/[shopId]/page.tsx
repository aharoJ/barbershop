"use client";

import { useQuery } from "@tanstack/react-query";
import { useParams, useRouter } from "next/navigation";
import Link from "next/link";
import { shopService } from "@/modules/shop/services";
import type { ShopResponse } from "@/modules/shop/types/shop.types";
import { Button } from "@/modules/shadcn/ui/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/modules/shadcn/ui/card";

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
    queryFn: () => shopService.getShop(shopId),
    enabled: !!shopId,
  });

  if (isLoading) {
    return (
      <div className="min-h-screen bg-gray-50 p-8 flex justify-center items-center">
        <p className="text-gray-600">Loading shop details...</p>
      </div>
    );
  }

  if (isError || !shop) {
    return (
      <div className="min-h-screen bg-gray-50 p-8 flex justify-center items-center">
        <p className="text-red-500">Failed to load shop</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-4xl mx-auto space-y-8">
        {/* Header Section */}
        <div className="flex justify-between items-center">
          <h1 className="text-3xl font-bold">Shop Details</h1>
          <div className="flex space-x-2">
            <Button asChild variant="default">
              <Link href={`/shops/${shopId}/edit`}>Edit Shop</Link>
            </Button>
            <Button asChild variant="outline">
              <Link href={`/shops/${shopId}/seats`}>Edit Seats</Link>
            </Button>
            <Button
              variant="outline"
              onClick={() => router.push(`/owners/dashboard`)}
            >
              Return to Dashboard
            </Button>
          </div>
        </div>

        {/* Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Shop Info Card */}
          <Card>
            <CardHeader>
              <CardTitle className="text-xl">{shop.name}</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-gray-600">{shop.address}</p>
            </CardContent>
          </Card>

          {/* Seats Card */}
          <Card>
            <CardHeader>
              <CardTitle className="text-xl">Seats</CardTitle>
            </CardHeader>
            <CardContent>
              {shop.seats && shop.seats.length > 0 ? (
                <ul className="space-y-2">
                  {shop.seats.map((seat) => (
                    <li
                      key={seat.id}
                      className="p-2 border rounded flex items-center justify-between transition-colors hover:bg-gray-50"
                    >
                      <span>
                        {seat.seatName} - {seat.barberFullName || "Unassigned"}
                      </span>
                    </li>
                  ))}
                </ul>
              ) : (
                <p className="text-gray-500">No seats available.</p>
              )}
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
