"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect } from "react";
import { useParams, useRouter } from "next/navigation";
import Link from "next/link";
import { Button } from "@/modules/shadcn/ui/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/modules/shadcn/ui/card";

import {
  shopSchema,
  type ShopPayload,
  ShopResponse,
} from "@/modules/shop/types/shop.types";
import { shopService } from "@/modules/shop/services";

export default function EditShopPage() {
  const router = useRouter();
  const params = useParams();
  const shopId = params.shopId as string;

  // Form for editing shop details
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<ShopPayload>({
    resolver: zodResolver(shopSchema),
  });

  // Fetch shop details on mount
  useEffect(() => {
    const fetchShop = async () => {
      try {
        const existingShop: ShopResponse = await shopService.getShop(shopId);
        reset({
          name: existingShop.name,
          address: existingShop.address,
        });
      } catch (error) {
        console.error("Failed to load shop:", error);
        router.push("/shops");
      }
    };

    if (shopId) fetchShop();
  }, [shopId, reset, router]);

  // Handler for updating shop details
  const onSubmitShop = async (payload: ShopPayload) => {
    try {
      const updatedShop = await shopService.updateShop(shopId, payload);
      router.push(`/shops/${updatedShop.id}`);
    } catch (error) {
      console.error("Update shop error:", error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-4xl mx-auto space-y-8">
        <div className="flex justify-between items-center">
          <h1 className="text-3xl font-bold">Edit Shop</h1>
          <Button asChild>
            <Link href={`/shops/${shopId}`}>Back to Shop</Link>
          </Button>
        </div>

        <Card>
          <CardHeader>
            <CardTitle>Edit Shop Details</CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit(onSubmitShop)} className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">
                  Shop Name
                </label>
                <input
                  {...register("name")}
                  className="w-full border px-3 py-2 rounded focus:outline-none focus:ring focus:border-blue-300"
                  placeholder="Enter shop name"
                />
                {errors.name && (
                  <p className="text-red-500 text-sm mt-1">
                    {errors.name.message}
                  </p>
                )}
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">
                  Address
                </label>
                <input
                  {...register("address")}
                  className="w-full border px-3 py-2 rounded focus:outline-none focus:ring focus:border-blue-300"
                  placeholder="Enter address"
                />
                {errors.address && (
                  <p className="text-red-500 text-sm mt-1">
                    {errors.address.message}
                  </p>
                )}
              </div>
              <Button type="submit" variant="default">
                Save Changes
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
