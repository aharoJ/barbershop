"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  barberSchema,
  type BarberPayload,
} from "@/modules/barber/types/barber.types";
import { useQuery } from "@tanstack/react-query";
import { useRouter, useParams } from "next/navigation";
import { useEffect } from "react";
import { barberService } from "@/modules/barber/services";
import { useAuthStore } from "@/stores/auth.store";

export default function EditBarberProfilePage() {
  const router = useRouter();
  const params = useParams();
  const userId = params.userId as string;
  const { user } = useAuthStore();
  
    // Basic security check (optional):
    if (user && user.id !== userId) {
      router.push("/unauthorized");
    }
  

  const {
    data: existingProfile,
    isLoading,
    isError,
  } = useQuery({
    queryKey: ["barber-profile"],
    queryFn:()=> barberService.getBarberProfile(userId),
    enabled: !!userId, // only run if userId is defined
  });

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<BarberPayload>({ resolver: zodResolver(barberSchema) });

  useEffect(() => {
    if (existingProfile && !isLoading) {
      reset({
        firstName: existingProfile.firstName,
        lastName: existingProfile.lastName,
        phoneNumber: existingProfile.phoneNumber,
      });
    }
  }, [existingProfile, isLoading, reset]);

  const onSubmit = async (data: BarberPayload) => {
    try {
      await barberService.updateBarberProfile(userId, data);
      router.push(`/barbers/${userId}`);
    } catch (error) {
      console.error("Update Barber Profile error:", error);
      // Handle error UI
    }
  };

  if (isLoading) return <div>Loading existing barber profile...</div>;
  if (isError) return <div>Failed to load barber profile</div>;

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-[450px]"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">
          Edit Barber Profile
        </h2>

        {/* FIRST NAME */}
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

        {/* LAST NAME */}
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

        {/* PHONE */}
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
          Save
        </button>
      </form>
    </div>
  );
}
