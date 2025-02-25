"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { shopSchema, type ShopPayload } from "@/modules/shop/types/shop.types";
import { shopService } from "@/modules/shop/services";
import { useRouter } from "next/navigation";

export default function CreateShopPage() {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ShopPayload>({
    resolver: zodResolver(shopSchema),
  });

  const onSubmit = async (payload: ShopPayload) => {
    try {
      const newShop = await shopService.createShop(payload);
      router.push(`/shops/${newShop.id}`);
    } catch (error) {
      console.error("Create shop error:", error);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Create a Shop</h1>
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
          Create
        </button>
      </form>
    </div>
  );
}
