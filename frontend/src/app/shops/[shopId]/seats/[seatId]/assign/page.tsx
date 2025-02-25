// @/app/shops/[shopId]/seats/[seatId]/assign/page.tsx
"use client";

import { useForm } from "react-hook-form";
import { useParams, useRouter } from "next/navigation";
import { seatService } from "@/modules/seat/services";
import { Button } from "@/modules/shadcn/ui/button";

type AssignBarberPayload = {
  associationId: number;
};

export default function AssignSeatPage() {
  const params = useParams();
  const router = useRouter();

  const shopId = params.shopId as string;
  const seatId = params.seatId as string;

  const { register, handleSubmit } = useForm<AssignBarberPayload>();

  const onSubmit = async (data: AssignBarberPayload) => {
    try {
      await seatService.assignSeatToBarber(shopId, seatId, data.associationId);
      router.push(`/shops/${shopId}`);
    } catch (error) {
      console.error("Assign seat error:", error);
    }
  };

  return (
    <div className="p-6 max-w-md mx-auto">
      <h2 className="text-2xl font-bold mb-6">
        Assign Seat {seatId} in Shop {shopId}
      </h2>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
        <div>
          <label className="block text-sm font-medium mb-1">
            Barber Association ID
          </label>
          <input
            type="number"
            {...register("associationId", { valueAsNumber: true })}
            className="w-full border px-3 py-2 rounded focus:outline-none focus:ring focus:border-blue-300"
          />
        </div>

        <Button type="submit" variant="default">
          Assign Barber
        </Button>
      </form>
    </div>
  );
}
