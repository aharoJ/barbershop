"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { shopSchema, type ShopPayload } from "@/modules/shop/types/shop.types";
import { shopServiceImpl } from "@/modules/shop/services";
import { useParams, useRouter } from "next/navigation";
import { useEffect } from "react";

export default function EditShopPage() {
  const router = useRouter();
  const params = useParams();
  const shopId = params.shopId as string;

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<ShopPayload>({
    resolver: zodResolver(shopSchema),
  });

  // Fetch existing shop data
  useEffect(() => {
    const fetchShop = async () => {
      try {
        const existingShop = await shopServiceImpl.getShop(shopId);
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

  const onSubmit = async (payload: ShopPayload) => {
    try {
      const updatedShop = await shopServiceImpl.updateShop(shopId, payload);
      router.push(`/shops/${updatedShop.id}`);
    } catch (error) {
      console.error("Update error:", error);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Edit Shop</h1>
      <form onSubmit={handleSubmit(onSubmit)} className="max-w-md space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">Shop Name</label>
          <input
            {...register("name")}
            className="w-full border px-3 py-2 rounded"
          />
          {errors.name && (
            <p className="text-red-500 text-sm">{errors.name.message}</p>
          )}
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Address</label>
          <input
            {...register("address")}
            className="w-full border px-3 py-2 rounded"
          />
          {errors.address && (
            <p className="text-red-500 text-sm">{errors.address.message}</p>
          )}
        </div>

        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Save Changes
        </button>
      </form>
    </div>
  );
}
