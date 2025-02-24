"use client";

import { useForm } from "react-hook-form";
import { shopServiceImpl } from "@/modules/shop/services";
import { useParams, useRouter } from "next/navigation";

type AssignBarberPayload = {
  associationId: number; // or a string
};

export default function AssignSeatPage() {
  const params = useParams();
  const router = useRouter();

  const shopId = params.shopId as string;
  const seatId = params.seatId as string;

  const { register, handleSubmit } = useForm<AssignBarberPayload>();

  const onSubmit = async (data: AssignBarberPayload) => {
    try {
      const seat = await shopServiceImpl.assignSeatToBarber(
        shopId,
        seatId,
        data.associationId
      );
      console.log("Assigned seat:", seat);
      router.push(`/shops/${shopId}`);
    } catch (error) {
      console.error("Assign seat error:", error);
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-xl font-bold mb-4">
        Assign Seat {seatId} in Shop {shopId}
      </h2>
      <form onSubmit={handleSubmit(onSubmit)} className="max-w-md space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">
            Barber Association ID
          </label>
          <input
            type="number"
            {...register("associationId", { valueAsNumber: true })}
            className="w-full border px-3 py-2 rounded"
          />
        </div>

        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Assign Barber
        </button>
      </form>
    </div>
  );
}
