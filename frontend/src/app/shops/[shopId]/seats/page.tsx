"use client";

import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useParams, useRouter } from "next/navigation";
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

type CreateSeatPayload = {
  seatName: string;
};

export default function SeatManagementPage() {
  const router = useRouter();
  const params = useParams();
  const shopId = params.shopId as string;

  const [seats, setSeats] = useState<SeatResponse[]>([]);
  // Store editingSeatId as a string (or null) to match route params and seatService
  const [editingSeatId, setEditingSeatId] = useState<string | null>(null);
  const [editingSeatName, setEditingSeatName] = useState<string>("");

  // Form for adding a new seat
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<CreateSeatPayload>({
    resolver: zodResolver(seatSchema),
    defaultValues: { seatName: "" },
  });

  // Fetch seats on mount
  useEffect(() => {
    const fetchSeats = async () => {
      try {
        const data = await seatService.getSeatsByShop(shopId);
        setSeats(data);
      } catch (error) {
        console.error("Failed to load seats:", error);
      }
    };
    if (shopId) fetchSeats();
  }, [shopId]);

  // Handler for adding a seat
  const onCreateSeat = async (payload: CreateSeatPayload) => {
    try {
      const newSeat = await seatService.addSeat(shopId, payload);
      setSeats((prev) => [...prev, newSeat]);
      reset({ seatName: "" });
    } catch (error) {
      console.error("Failed to create seat:", error);
    }
  };

  // Begin editing
  const startEditing = (seat: SeatResponse) => {
    setEditingSeatId(String(seat.id)); // <-- changed
    setEditingSeatName(seat.seatName);
  };

  // Cancel editing
  const cancelEditing = () => {
    setEditingSeatId(null);
    setEditingSeatName("");
  };

  // Save seat edits
  const saveEdit = async (numericSeatId: number) => {
    try {
      // Convert numeric seat ID to string before passing to seatService
      const seatIdAsString = String(numericSeatId); // <-- changed
      const updatedSeat = await seatService.updateSeatInfo(
        shopId,
        seatIdAsString,
        {
          seatName: editingSeatName,
        },
      );
      setSeats((prev) =>
        prev.map((seat) => (seat.id === numericSeatId ? updatedSeat : seat)),
      );
      cancelEditing();
    } catch (error) {
      console.error("Failed to update seat:", error);
    }
  };

  // Remove seat
  const removeSeat = async (numericSeatId: number) => {
    try {
      const seatIdAsString = String(numericSeatId); // <-- changed
      await seatService.removeSeat(shopId, seatIdAsString);
      setSeats((prev) => prev.filter((seat) => seat.id !== numericSeatId));
    } catch (error) {
      console.error("Failed to remove seat:", error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-4xl mx-auto space-y-8">
        <div className="flex justify-between items-center">
          <h1 className="text-3xl font-bold">Seat Management</h1>
          <Button asChild>
            <Link href={`/shops/${shopId}`}>Back to Shop</Link>
          </Button>
        </div>

        {/* Create New Seat */}
        <Card>
          <CardHeader>
            <CardTitle>Add a New Seat</CardTitle>
          </CardHeader>
          <CardContent>
            <form
              onSubmit={handleSubmit(onCreateSeat)}
              className="space-y-4 flex flex-col sm:flex-row items-start sm:items-end"
            >
              <div className="flex-1">
                <label className="block text-sm font-medium mb-1">
                  Seat Name
                </label>
                <input
                  {...register("seatName")}
                  className="w-full border px-3 py-2 rounded focus:outline-none focus:ring focus:border-blue-300"
                  placeholder="e.g. Seat #1"
                />
                {errors.seatName && (
                  <p className="text-red-500 text-sm mt-1">
                    {errors.seatName.message}
                  </p>
                )}
              </div>
              <Button
                type="submit"
                variant="secondary"
                className="mt-4 sm:mt-0"
              >
                Create Seat
              </Button>
            </form>
          </CardContent>
        </Card>

        {/* Existing Seats */}
        <Card>
          <CardHeader>
            <CardTitle>Existing Seats</CardTitle>
          </CardHeader>
          <CardContent>
            {seats.length === 0 ? (
              <p className="text-gray-500">No seats found.</p>
            ) : (
              <ul className="space-y-3">
                {seats.map((seat) => {
                  const isEditing = editingSeatId === String(seat.id); // <-- changed
                  return (
                    <li
                      key={seat.id}
                      className="p-3 border rounded flex justify-between items-center transition-colors hover:bg-gray-50"
                    >
                      {isEditing ? (
                        <>
                          <input
                            value={editingSeatName}
                            onChange={(e) => setEditingSeatName(e.target.value)}
                            className="border px-2 py-1 rounded focus:outline-none focus:ring focus:border-blue-300"
                          />
                          <div className="flex space-x-2">
                            <Button
                              variant="default"
                              onClick={() => saveEdit(seat.id)}
                            >
                              Save
                            </Button>
                            <Button
                              variant="destructive"
                              onClick={cancelEditing}
                            >
                              Cancel
                            </Button>
                          </div>
                        </>
                      ) : (
                        <>
                          <span>
                            {seat.seatName}{" "}
                            {seat.barberFullName
                              ? `- ${seat.barberFullName}`
                              : "- Unassigned"}
                          </span>
                          <div className="flex space-x-2">
                            <Button
                              variant="default"
                              onClick={() => startEditing(seat)}
                            >
                              Edit
                            </Button>
                            <Button
                              variant="destructive"
                              onClick={() => removeSeat(seat.id)}
                            >
                              Delete
                            </Button>
                            <Button asChild variant="secondary">
                              <Link
                                href={`/shops/${shopId}/seats/${seat.id}/assign`}
                              >
                                Assign
                              </Link>
                            </Button>
                          </div>
                        </>
                      )}
                    </li>
                  );
                })}
              </ul>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
