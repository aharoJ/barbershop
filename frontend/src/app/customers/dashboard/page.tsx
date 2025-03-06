// app/customers/dashboard/page.tsx
"use client";
import { useQuery } from "@tanstack/react-query";
import { customerService } from "@/modules/customer/services";
import { CustomerResponse } from "@/modules/customer/types/customer.types";
import { Button } from "@/modules/shadcn/ui/button";
import { Loader2, LogOut, Settings, Trash, User } from "lucide-react";
import Link from "next/link";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/modules/shadcn/ui/card";
import { Badge } from "@/modules/shadcn/ui/badge";
import { useAuthStore } from "@/stores/auth.store";
import { authService } from "@/modules/auth/services/auth.service";
import { useRouter } from "next/navigation";

export default function CustomerDashboard() {
  const router = useRouter();

  const { data: customerProfile, isLoading: isLoadingProfile } =
    useQuery<CustomerResponse>({
      queryKey: ["customer-profile"],
      queryFn: () => customerService.getCustomerProfile(),
    });

  const handleLogout = async () => {
    const { refreshToken } = useAuthStore.getState();
    if (refreshToken) {
      try {
        await authService.logout(refreshToken);
      } catch (error) {
        console.log("Logout errro: ", error);
      }
    }
    useAuthStore.getState().logout();
    router.push("/login");
  };

  if (isLoadingProfile) {
    return (
      <div className="flex items-center justify-center h-screen">
        <Loader2 className="h-12 w-12 animate-spin text-blue-600" />
      </div>
    );
  }

  if (!customerProfile) {
    return (
      <div className="p-8 text-red-600">Failed to load dashboard data</div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">
              Customer Dashboard
            </h1>
            <p className="text-gray-600 mt-2">
              Welcome back, {customerProfile.firstName} 👋
            </p>
          </div>
          <div className="flex gap-4">
            <Button asChild className="bg-red-400/60 hover:bg-red-500">
              <Link href="profile/delete">
                <Trash className="mr-2 h-4 w-4" />
                Delete Profile
              </Link>
            </Button>

            {/* Logout */}
            <Button onClick={handleLogout} className="hover:bg-black/20">
              <LogOut className="mr-2 h-4 w-4" />
              Logout
            </Button>

            <Button asChild>
              <Link href="/customers/profile/edit">
                <Settings className="mr-2 h-4 w-4" />
                Edit Profile
              </Link>
            </Button>
          </div>
        </div>

        {/* Profile Card */}
        <Card className="max-w-3xl">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <User className="h-6 w-6" />
              <span>Profile Information</span>
            </CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="text-sm text-gray-500">First Name</p>
                <p className="font-medium">{customerProfile.firstName}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Last Name</p>
                <p className="font-medium">{customerProfile.lastName}</p>
              </div>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="text-sm text-gray-500">Email</p>
                <p className="font-medium">{customerProfile.email}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Phone</p>
                <p className="font-medium">
                  {customerProfile.phoneNumber || "-"}
                </p>
              </div>
            </div>

            {customerProfile.dateOfBirth && (
              <div>
                <p className="text-sm text-gray-500">Date of Birth</p>
                <p className="font-medium">{customerProfile.dateOfBirth}</p>
              </div>
            )}

            {customerProfile.address && (
              <div className="space-y-2">
                <Badge variant="outline" className="text-sm">
                  Address
                </Badge>
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <p className="text-sm text-gray-500">Street</p>
                    <p className="font-medium">
                      {customerProfile.address.street}
                    </p>
                  </div>
                  <div>
                    <p className="text-sm text-gray-500">City</p>
                    <p className="font-medium">
                      {customerProfile.address.city}
                    </p>
                  </div>
                  <div>
                    <p className="text-sm text-gray-500">State</p>
                    <p className="font-medium">
                      {customerProfile.address.state}
                    </p>
                  </div>
                  <div>
                    <p className="text-sm text-gray-500">Postal Code</p>
                    <p className="font-medium">
                      {customerProfile.address.postalCode}
                    </p>
                  </div>
                  <div>
                    <p className="text-sm text-gray-500">Country</p>
                    <p className="font-medium">
                      {customerProfile.address.country}
                    </p>
                  </div>
                </div>
              </div>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
