// @/app/shops/[shopId]/edit/page.tsx
"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useEffect, useState } from "react";
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
import { seatSchema, type SeatResponse } from "@/modules/shop/types/seat.types";
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

  // Form for adding a seat
  const {
    register: seatRegister,
    handleSubmit: seatHandleSubmit,
    formState: { errors: seatErrors },
    reset: seatReset,
  } = useForm<{ seatName: string }>({
    resolver: zodResolver(seatSchema),
    defaultValues: { seatName: "" },
  });

  // Local state for seats
  const [seats, setSeats] = useState<SeatResponse[]>([]);

  // Fetch shop details (including seats) on mount
  useEffect(() => {
    const fetchShop = async () => {
      try {
        const existingShop: ShopResponse = await shopService.getShop(shopId);
        reset({
          name: existingShop.name,
          address: existingShop.address,
        });
        // Assume the shop object includes seats
        setSeats(existingShop.seats || []);
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
      console.error("Update error:", error);
    }
  };

  // Handler for adding a new seat
  const onSubmitSeat = async (payload: { seatName: string }) => {
    try {
      const newSeat = await shopService.addSeat(shopId, payload);
      setSeats((prev) => [...prev, newSeat]);
      seatReset({ seatName: "" });
    } catch (error) {
      console.error("Add seat error:", error);
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

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {/* Shop Edit Card */}
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
                    className="w-full border px-3 py-2 rounded"
                    placeholder="Enter shop name"
                  />
                  {errors.name && (
                    <p className="text-red-500 text-sm">
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
                    className="w-full border px-3 py-2 rounded"
                    placeholder="Enter address"
                  />
                  {errors.address && (
                    <p className="text-red-500 text-sm">
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

          {/* Seats Management Card */}
          <Card>
            <CardHeader>
              <CardTitle>Manage Seats</CardTitle>
            </CardHeader>
            <CardContent>
              <form
                onSubmit={seatHandleSubmit(onSubmitSeat)}
                className="flex flex-col space-y-4"
              >
                <div className="flex space-x-2">
                  <input
                    {...seatRegister("seatName")}
                    className="w-full border px-3 py-2 rounded"
                    placeholder="Seat Name"
                  />
                  <Button type="submit" variant="secondary">
                    Add Seat
                  </Button>
                </div>
                {seatErrors.seatName && (
                  <p className="text-red-500 text-sm">
                    {seatErrors.seatName.message}
                  </p>
                )}
              </form>

              <div className="mt-6">
                <h3 className="text-lg font-medium">Existing Seats</h3>
                {seats.length === 0 ? (
                  <p className="text-gray-500 mt-2">No seats added yet.</p>
                ) : (
                  <ul className="mt-2 space-y-2">
                    {seats.map((seat) => (
                      <li key={seat.id} className="p-2 border rounded">
                        {seat.seatName}
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}
