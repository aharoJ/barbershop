// @app/(customer)/profile/create/page.tsx
"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { customerService } from "@/services/customer.service";
import {
  customerSchema,
  type CustomerPayload,
  Gender,
  MarketingNotifications,
} from "@/types/customer.types";

export default function CreateProfilePage() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<CustomerPayload>({
    resolver: zodResolver(customerSchema),
  });
  const router = useRouter();

  const onSubmit = async (data: CustomerPayload) => {
    try {
      await customerService.createCustomer(data);
      router.push("/dashboard");
    } catch (error) {
      console.error("Profile creation failed:", error);
      alert("Failed to create profile. Please try again.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-full max-w-2xl"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">
          Complete Your Profile
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {/* First Name */}
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">
              First Name *
            </label>
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
            <label className="block text-sm font-medium mb-1">
              Last Name *
            </label>
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
            <label className="block text-sm font-medium mb-1">
              Phone Number *
            </label>
            <input
              type="tel"
              {...register("phoneNumber")}
              className="w-full px-3 py-2 border rounded-md"
            />
            {errors.phoneNumber && (
              <span className="text-red-500 text-sm">
                {errors.phoneNumber.message}
              </span>
            )}
          </div>

          {/* Date of Birth */}
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">
              Date of Birth *
            </label>
            <input
              type="date"
              {...register("dateOfBirth")}
              className="w-full px-3 py-2 border rounded-md"
            />
            {errors.dateOfBirth && (
              <span className="text-red-500 text-sm">
                {errors.dateOfBirth.message}
              </span>
            )}
          </div>

          {/* Gender */}
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">Gender *</label>
            <select
              {...register("gender")}
              className="w-full px-3 py-2 border rounded-md"
            >
              {Gender.map((option) => (
                <option key={option} value={option}>
                  {option.replace(/_/g, " ").toLowerCase()}
                </option>
              ))}
            </select>
          </div>

          {/* Marketing Notifications */}
          <div className="mb-4">
            <label className="block text-sm font-medium mb-1">
              Notifications *
            </label>
            <select
              {...register("marketingNotifications")}
              className="w-full px-3 py-2 border rounded-md"
            >
              {MarketingNotifications.map((option) => (
                <option key={option} value={option}>
                  {option.toLowerCase()}
                </option>
              ))}
            </select>
          </div>

          {/* Address Fields */}
          <div className="col-span-2 space-y-4">
            <h3 className="text-lg font-semibold">Address Information</h3>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium mb-1">
                  Street *
                </label>
                <input
                  {...register("address.street")}
                  className="w-full px-3 py-2 border rounded-md"
                />
                {errors.address?.street && (
                  <span className="text-red-500 text-sm">
                    {errors.address.street.message}
                  </span>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">City *</label>
                <input
                  {...register("address.city")}
                  className="w-full px-3 py-2 border rounded-md"
                />
                {errors.address?.city && (
                  <span className="text-red-500 text-sm">
                    {errors.address.city.message}
                  </span>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">
                  State *
                </label>
                <input
                  {...register("address.state")}
                  className="w-full px-3 py-2 border rounded-md"
                />
                {errors.address?.state && (
                  <span className="text-red-500 text-sm">
                    {errors.address.state.message}
                  </span>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">
                  Postal Code *
                </label>
                <input
                  {...register("address.postalCode")}
                  className="w-full px-3 py-2 border rounded-md"
                />
                {errors.address?.postalCode && (
                  <span className="text-red-500 text-sm">
                    {errors.address.postalCode.message}
                  </span>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">
                  Country *
                </label>
                <input
                  {...register("address.country")}
                  className="w-full px-3 py-2 border rounded-md"
                />
                {errors.address?.country && (
                  <span className="text-red-500 text-sm">
                    {errors.address.country.message}
                  </span>
                )}
              </div>
            </div>
          </div>
        </div>

        <button
          type="submit"
          className="w-full mt-6 bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Complete Profile
        </button>
      </form>
    </div>
  );
}
