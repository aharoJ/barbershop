"use client";
// @/app/barbers/profile/edit/page.tsx
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  barberSchema,
  type BarberPayload,
} from "@/modules/barber/types/barber.types";
import { useQuery } from "@tanstack/react-query";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { barberService } from "@/modules/barber/services";
import { Button } from "@/modules/shadcn/ui/button";
import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
} from "@/modules/shadcn/ui/card";
import { Label } from "@radix-ui/react-dropdown-menu";
import { Input } from "@/modules/shadcn/ui/input";

export default function EditBarberProfilePage() {
  const router = useRouter();
  const {
    data: existingProfile,
    isLoading,
    isError,
  } = useQuery({
    queryKey: ["barber-profile"],
    queryFn: () => barberService.getBarberProfile(),
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
      await barberService.updateBarberProfile(data);
      router.push(`/barbers/dashboard`);
    } catch (error) {
      console.error("Update Barber Profile error:", error);
    }
  };

  if (isLoading) return <div>Loading existing barber profile...</div>;
  if (isError) return <div>Failed to load barber profile</div>;

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-2xl mx-auto">
        <Card>
          <CardHeader>
            <CardTitle className="text-2xl font-bold text-center">
              Edit Barber Profile
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
