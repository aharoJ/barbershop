"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  barberSchema,
  type BarberPayload,
} from "@/modules/barber/types/barber.types";
import { useRouter, useParams } from "next/navigation";
import { barberServiceImpl } from "@/modules/barber/services";
import { useAuthStore } from "@/stores/auth.store";

export default function CreateBarberProfilePage() {
  const router = useRouter();
  const params = useParams();
  const userId = params.userId as string;
  const { user } = useAuthStore();

  // Basic security check (optional):
  if (user && user.id !== userId) {
    router.push("/unauthorized");
  }

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<BarberPayload>({
    resolver: zodResolver(barberSchema),
    defaultValues: {
      firstName: "",
      lastName: "",
      phoneNumber: "",
    },
  });

  const onSubmit = async (data: BarberPayload) => {
    try {
      await barberServiceImpl.createBarberProfile(data);
      // Go to barber "dashboard"
      router.push(`/barbers/${userId}`);
    } catch (error) {
      console.error("Create Barber Profile error:", error);
      // Handle error UI
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-[450px]"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">
          Create Barber Profile
        </h2>

        {/* First Name */}
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">First Name</label>
          <input
            {...register("firstName")}
            className="w-full px-3 py-2 border rounded-md"
          />
          {errors.firstName && (
            <span className="text-red-500 text-sm">
              {errors.firstName.message}
            </span>
          )}
        </div>

        {/* Last Name */}
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Last Name</label>
          <input
            {...register("lastName")}
            className="w-full px-3 py-2 border rounded-md"
          />
          {errors.lastName && (
            <span className="text-red-500 text-sm">
              {errors.lastName.message}
            </span>
          )}
        </div>

        {/* Phone Number */}
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Phone Number</label>
          <input
            {...register("phoneNumber")}
            className="w-full px-3 py-2 border rounded-md"
          />
          {errors.phoneNumber && (
            <span className="text-red-500 text-sm">
              {errors.phoneNumber.message}
            </span>
          )}
        </div>

        <button
          type="submit"
          className="w-full bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-700"
        >
          Create
        </button>
      </form>
    </div>
  );
}
