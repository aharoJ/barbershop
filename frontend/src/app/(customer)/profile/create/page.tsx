// @app/(customer)/profile/create/page.tsx

"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { customerService } from "@/services/customer.service";
import { customerSchema, type CustomerPayload } from "@/types/customer.types";

export default function CreateProfilePage() {
  const { register, handleSubmit } = useForm<CustomerPayload>({
    resolver: zodResolver(customerSchema),
  });
  const router = useRouter();

  const onSubmit = async (data: CustomerPayload) => {
    try {
      await customerService.createCustomer(data);
      router.push("/dashboard");
    } catch (error) {
      console.error("Profile creation failed:", error);
      // Show error message to user
      alert("Failed to create profile. Please try again.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Create Profile</h2>

        {/* First Name  */}
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">First Name</label>
          <input
            {...register("firstName")}
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/* Last Name  */}
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Last Name</label>
          <input
            {...register("lastName")}
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/* Phone Number  */}
        <div className="mb-6">
          <label className="block text-sm font-medium mb-1">Phone Number</label>
          <input
            type="phone"
            {...register("phoneNumber")} // CHANGED FROM "phone" TO "phoneNumber"
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        {/*   REMOVE THIS AS IT DONT MATCH BACKEND
          <div className="mb-6">
            <label className="block text-sm font-medium mb-1">Email</label>
            <input
              type="email"
              {...register("email")}
              className="w-full px-3 py-2 border rounded-md"
            />
          </div>
        */}
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Complete Profile
        </button>
      </form>
    </div>
  );
}
