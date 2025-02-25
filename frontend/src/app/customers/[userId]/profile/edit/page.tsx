"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { customerSchema, type CustomerPayload } from "@/modules/customer/types/customer.types";
import { useQuery } from "@tanstack/react-query";
import { useRouter, useParams } from "next/navigation";
import { useEffect } from "react";
import { customerService } from "@/modules/customer/services";

export default function EditCustomerProfilePage() {
  const router = useRouter();
  const params = useParams();
  const userId = params.userId as string;

  const {
    data: existingProfile,
    isLoading,
    isError,
  } = useQuery({
    queryKey: ["customer-profile"],
    queryFn: () => customerService.getCustomerProfile(userId),
    enabled: !!userId, // only run if userId is defined
  });

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<CustomerPayload>({ resolver: zodResolver(customerSchema) });

  useEffect(() => {
    if (existingProfile && !isLoading) {
      reset({
        firstName: existingProfile.firstName,
        lastName: existingProfile.lastName,
        phoneNumber: existingProfile.phoneNumber ?? "",
        dateOfBirth: existingProfile.dateOfBirth ?? "",
        address: existingProfile.address || {
          street: "",
          city: "",
          state: "",
          postalCode: "",
          country: "",
        },
      });
    }
  }, [existingProfile, isLoading, reset]);

  const onSubmit = async (data: CustomerPayload) => {
    try {
      await customerService.updateCustomerProfile(userId, data);
      router.push(`/customers/${userId}`);
    } catch (error) {
      console.error("Update Customer Profile error:", error);
    }
  };

  if (isLoading) return <div>Loading customer profile...</div>;
  if (isError) return <div>Failed to load customer profile</div>;

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-[500px]"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">
          Edit Customer Profile
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

        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">
            Date of Birth
          </label>
          <input
            {...register("dateOfBirth")}
            className="w-full px-3 py-2 border rounded-md"
            placeholder="YYYY-MM-DD"
          />
          {errors.dateOfBirth && (
            <span className="text-red-500 text-sm">
              {errors.dateOfBirth.message}
            </span>
          )}
        </div>

        {/* ADDRESS FIELDS */}
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Street</label>
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

        <div className="grid grid-cols-2 gap-4 mb-4">
          <div>
            <label className="block text-sm font-medium mb-1">City</label>
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
            <label className="block text-sm font-medium mb-1">State</label>
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
        </div>

        <div className="grid grid-cols-2 gap-4 mb-4">
          <div>
            <label className="block text-sm font-medium mb-1">
              Postal Code
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
            <label className="block text-sm font-medium mb-1">Country</label>
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

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Save
        </button>
      </form>
    </div>
  );
}
