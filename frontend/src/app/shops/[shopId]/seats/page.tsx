// @/app/shops/[shopId]/seats/page.tsx
"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { seatSchema, type SeatResponse } from "@/modules/shop/types/seat.types";
import { shopService } from "@/modules/shop/services";
import { useParams, useRouter } from "next/navigation";

export default function AddSeatPage() {
  const params = useParams();
  const router = useRouter();
  const shopId = params.shopId as string;

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(seatSchema),
    defaultValues: {
      seatName: "",
    },
  });

  const onSubmit = async (payload: { seatName: string }) => {
    try {
      const seat = await shopService.addSeat(shopId, payload);
      console.log("Added seat:", seat);
      router.push(`/shops/${shopId}`);
    } catch (error) {
      console.error("Add seat error:", error);
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-xl font-bold mb-4">Add a Seat to Shop {shopId}</h2>
      <form onSubmit={handleSubmit(onSubmit)} className="max-w-md space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">Seat Name</label>
          <input
            {...register("seatName")}
            className="w-full border px-3 py-2 rounded"
          />
          {errors.seatName && (
            <p className="text-red-500 text-sm">{errors.seatName.message}</p>
          )}
        </div>

        <button
          type="submit"
          className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          Add Seat
        </button>
      </form>
    </div>
  );
}
