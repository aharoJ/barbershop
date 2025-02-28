"use client";

import { useEffect } from "react";
import { useParams, useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import Link from "next/link";
import { Button } from "@/modules/shadcn/ui/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/modules/shadcn/ui/card";
import { seatSchema, type SeatResponse } from "@/modules/seat/types/seat.types";
import { seatService } from "@/modules/seat/services";

export default function EditSeatPage() {
  const router = useRouter();
  const params = useParams();
  const shopId = params.shopId as string;
  const seatId = params.seatId as string;

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<{ seatName: string }>({
    resolver: zodResolver(seatSchema),
  });

  useEffect(() => {
    const fetchSeat = async () => {
      try {
        const seat: SeatResponse = await seatService.getSeatById(
          shopId,
          seatId,
        );
        reset({ seatName: seat.seatName });
      } catch (error) {
        console.error("Failed to load seat:", error);
        router.push(`/shops/${shopId}/seats`);
      }
    };

    if (shopId && seatId) fetchSeat();
  }, [shopId, seatId, reset, router]);

  const onSubmit = async (payload: { seatName: string }) => {
    try {
      await seatService.updateSeatInfo(shopId, seatId, payload);
      router.push(`/shops/${shopId}/seats`);
    } catch (error) {
      console.error("Failed to update seat:", error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-4xl mx-auto space-y-8">
        <div className="flex justify-between items-center">
          <h1 className="text-3xl font-bold">Edit Seat</h1>
          <Button asChild>
            <Link href={`/shops/${shopId}/seats`}>Back to Seats</Link>
          </Button>
        </div>
        <Card>
          <CardHeader>
            <CardTitle>Edit Seat Information</CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">
                  Seat Name
                </label>
                <input
                  {...register("seatName")}
                  className="w-full border px-3 py-2 rounded"
                  placeholder="Enter seat name"
                />
                {errors.seatName && (
                  <p className="text-red-500 text-sm">
                    {errors.seatName.message}
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
