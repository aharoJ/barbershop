// @/app/shops/create/page.tsx
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
    <div className="p-6 max-w-lg mx-auto">
      <h1 className="text-2xl font-bold mb-6">Create a Shop</h1>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
        <div>
          <label className="block text-sm font-medium mb-1">Shop Name</label>
          <input
            {...register("name")}
            className="w-full border px-3 py-2 rounded focus:outline-none focus:ring focus:border-blue-300"
            placeholder="Enter shop name"
          />
          {errors.name && (
            <p className="text-red-500 text-sm mt-1">{errors.name.message}</p>
          )}
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Address</label>
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

        <button
          type="submit"
          className="w-full bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition-colors duration-200"
        >
          Create
        </button>
      </form>
    </div>
  );
}
