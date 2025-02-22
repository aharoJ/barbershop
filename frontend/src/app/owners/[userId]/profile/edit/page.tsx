"use client";
// app/owners/[userId]/profile/edit/page.tx
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { ownerSchema, type OwnerPayload } from "@/modules/owner/types/owner.types";
import { useQuery } from "@tanstack/react-query";
import { useRouter, useParams } from "next/navigation";
import { useEffect } from "react";
import { ownerServiceImpl } from "@/modules/owner/services";
import { useAuthStore } from "@/stores/auth.store";

export default function EditOwnerProfilePage() {
  const router = useRouter();
  const params = useParams();
  const userId = params.userId as string; // Casting to a single string
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
    queryKey: ["owner-profile", userId],
    queryFn: () => ownerServiceImpl.getOwnerProfile(userId),
    enabled: !!userId, // only run if userId is defined
  });

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<OwnerPayload>({ resolver: zodResolver(ownerSchema) });

  useEffect(() => {
    if (existingProfile && !isLoading) {
      reset({
        firstName: existingProfile.firstName,
        lastName: existingProfile.lastName,
        phoneNumber: existingProfile.phoneNumber,
      });
    }
  }, [existingProfile, isLoading, reset]);

  const onSubmit = async (data: OwnerPayload) => {
    try {
      await ownerServiceImpl.updateOwnerProfile(userId, data);
      router.push(`/owners/${userId}`);
    } catch (error) {
      console.error("Update Owner Profile error:", error);
    }
  };

  if (isLoading) return <div>Loading existing profile...</div>;
  if (isError) return <div>Failed to load profile. Please try again.</div>;

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-[500px]"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">
          Edit Owner Profile
        </h2>

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
          className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Save Changes
        </button>
      </form>
    </div>
  );
}
