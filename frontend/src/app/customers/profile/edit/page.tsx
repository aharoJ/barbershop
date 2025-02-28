"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  customerSchema,
  type CustomerPayload,
} from "@/modules/customer/types/customer.types";
import { useQuery } from "@tanstack/react-query";
import { useRouter, useParams } from "next/navigation";
import { useEffect } from "react";
import { customerService } from "@/modules/customer/services";
import { Button } from "@/modules/shadcn/ui/button";
import { Card, CardHeader, CardTitle, CardContent } from "@/modules/shadcn/ui/card";
import { Label } from "@radix-ui/react-dropdown-menu";
import { Input } from "@/modules/shadcn/ui/input";

export default function EditCustomerProfilePage() {
  const router = useRouter();

  const {data: existingProfile,isLoading,isError,} = 
  useQuery({
    queryKey: ["customer-profile"],
    queryFn: () => customerService.getCustomerProfile(),
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
      await customerService.updateCustomerProfile(data);
      router.push(`/customers/dashboard`);
    } catch (error) {
      console.error("Update Customer Profile error:", error);
    }
  };

  if (isLoading) return <div>Loading customer profile...</div>;
  if (isError) return <div>Failed to load customer profile</div>;

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-2xl mx-auto">
        <Card>
          <CardHeader>
            <CardTitle className="text-2xl font-bold text-center">
              Edit Customer Profile
            </CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
              <div className="grid grid-cols-2 gap-6">
                <div className="space-y-2">
                  <Label>First Name</Label>
                  <Input {...register("firstName")} />
                  {errors.firstName && (
                    <p className="text-sm text-red-500">
                      {errors.firstName.message}
                    </p>
                  )}
                </div>

                <div className="space-y-2">
                  <Label>Last Name</Label>
                  <Input {...register("lastName")} />
                  {errors.lastName && (
                    <p className="text-sm text-red-500">
                      {errors.lastName.message}
                    </p>
                  )}
                </div>

                <div className="space-y-2">
                  <Label>Phone Number</Label>
                  <Input {...register("phoneNumber")} />
                  {errors.phoneNumber && (
                    <p className="text-sm text-red-500">
                      {errors.phoneNumber.message}
                    </p>
                  )}
                </div>

                <div className="space-y-2">
                  <Label>Date of Birth</Label>
                  <Input type="date" {...register("dateOfBirth")} />
                  {errors.dateOfBirth && (
                    <p className="text-sm text-red-500">
                      {errors.dateOfBirth.message}
                    </p>
                  )}
                </div>
              </div>

              <div className="space-y-6">
                <h3 className="text-lg font-semibold">Address Information</h3>
                <div className="grid grid-cols-2 gap-6">
                  <div className="space-y-2">
                    <Label>Street</Label>
                    <Input {...register("address.street")} />
                    {errors.address?.street && (
                      <p className="text-sm text-red-500">
                        {errors.address.street.message}
                      </p>
                    )}
                  </div>

                  <div className="space-y-2">
                    <Label>City</Label>
                    <Input {...register("address.city")} />
                    {errors.address?.city && (
                      <p className="text-sm text-red-500">
                        {errors.address.city.message}
                      </p>
                    )}
                  </div>

                  <div className="space-y-2">
                    <Label>State</Label>
                    <Input {...register("address.state")} />
                    {errors.address?.state && (
                      <p className="text-sm text-red-500">
                        {errors.address.state.message}
                      </p>
                    )}
                  </div>

                  <div className="space-y-2">
                    <Label>Postal Code</Label>
                    <Input {...register("address.postalCode")} />
                    {errors.address?.postalCode && (
                      <p className="text-sm text-red-500">
                        {errors.address.postalCode.message}
                      </p>
                    )}
                  </div>

                  <div className="space-y-2">
                    <Label>Country</Label>
                    <Input {...register("address.country")} />
                    {errors.address?.country && (
                      <p className="text-sm text-red-500">
                        {errors.address.country.message}
                      </p>
                    )}
                  </div>
                </div>
              </div>

              <Button type="submit" className="w-full">
                Save
              </Button>
            </form>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}