"use client";
// app/owners/profile/createpage.tx
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  ownerSchema,
  type OwnerPayload,
} from "@/modules/owner/types/owner.types";
import { useRouter, useParams } from "next/navigation";
import { useAuthStore } from "@/stores/auth.store";
import { ownerService } from "@/modules/owner/services";

export default function CreateOwnerProfilePage() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<OwnerPayload>({
    resolver: zodResolver(ownerSchema),
  });

  const router = useRouter();

  const { user } = useAuthStore();

  const onSubmit = async (data: OwnerPayload) => {
    try {
      await ownerService.createOwnerProfile(data);
      router.push(`/owners/dashboard`);
    } catch (error) {
      console.error("Create Owner Profile error:", error);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-[500px]"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">
          Create Owner Profile
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
          Create
        </button>
      </form>
    </div>
  );
}
