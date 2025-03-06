// app/barbers/dashboard/page.tsx
"use client";
import { useQuery } from "@tanstack/react-query";
import { barberService } from "@/modules/barber/services";
import { BarberResponse } from "@/modules/barber/types/barber.types";
import { Button } from "@/modules/shadcn/ui/button";
import { Loader2, LogOut, Settings, Trash, User } from "lucide-react";
import Link from "next/link";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/modules/shadcn/ui/card";
import { useAuthStore } from "@/stores/auth.store";
import { authService } from "@/modules/auth/services/auth.service";
import { useRouter } from "next/navigation";


export default function BarberDashboard() {
  const router = useRouter();

  const { data: barberProfile, isLoading: isLoadingProfile } =
    useQuery<BarberResponse>({
      queryKey: ["barber-profile"],
      queryFn: () => barberService.getBarberProfile(),
    });

  // updated handleLogout
  const handleLogout = async () => {
    // 1) Grab refresh token from our store
    const { refreshToken } = useAuthStore.getState();

    if (refreshToken) {
      try {
        // 2) Call backend to invalidate refresh token
        await authService.logout(refreshToken);
      } catch (err) {
        console.error("Logout error:", err);
      }
    }

    // 3) Clear tokens from the store (this sets user=null, tokens=null)
    useAuthStore.getState().logout();

    // 4) Redirect to login
    router.push("/login");
  };

  if (isLoadingProfile) {
    return (
      <div className="flex items-center justify-center h-screen">
        <Loader2 className="h-12 w-12 animate-spin text-blue-600" />
      </div>
    );
  }

  if (!barberProfile) {
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
              Barber Dashboard
            </h1>
            <p className="text-gray-600 mt-2">
              Welcome back, {barberProfile.firstName} 👋
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
              <Link href="/barbers/profile/edit">
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
                <p className="font-medium">{barberProfile.firstName}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Last Name</p>
                <p className="font-medium">{barberProfile.lastName}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Email</p>
                <p className="font-medium">{barberProfile.email}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Phone</p>
                <p className="font-medium">
                  {barberProfile.phoneNumber || "-"}
                </p>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
