// app/dashboard/page.tsx
"use client";
import { useQuery } from "@tanstack/react-query";
import { ownerService } from "@/modules/owner/services";
import { useRouter } from "next/navigation";
import { useAuthStore } from "@/stores/auth.store";
import { OwnerResponse } from "@/modules/owner/types/owner.types";
import { ShopResponse } from "@/modules/shop/types/shop.types";
import { shopService } from "@/modules/shop/services";
import { Button } from "@/modules/shadcn/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/modules/shadcn/ui/card";

import { Badge } from "@/modules/shadcn/ui/badge";
import { Loader2, PlusCircle, Settings, User } from "lucide-react";
import Link from "next/link";

export default function OwnerDashboard() {
  const router = useRouter();
  const { user } = useAuthStore();

  // Fetch data
  const { data: ownerProfile, isLoading: isLoadingProfile } = useQuery<OwnerResponse>({
    queryKey: ["owner-profile"],
    queryFn: () => ownerService.getOwnerProfile(),
  });

  const { data: myShops, isLoading: isLoadingShops } = useQuery<ShopResponse[]>({
    queryKey: ["my-shops"],
    queryFn: () => shopService.getShopsByOwner(),
  });

  if (isLoadingProfile || isLoadingShops) {
    return (
      <div className="flex items-center justify-center h-screen">
        <Loader2 className="h-12 w-12 animate-spin text-blue-600" />
      </div>
    );
  }

  if (!ownerProfile || !myShops) {
    return <div className="p-8 text-red-600">Failed to load dashboard data</div>;
  }

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex justify-between items-center mb-8">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">Owner Dashboard</h1>
            <p className="text-gray-600 mt-2">
              Welcome back, {ownerProfile.firstName} 👋
            </p>
          </div>
          <Button asChild>
            <Link href="profile/edit">
              <Settings className="mr-2 h-4 w-4" />
              Edit Profile
            </Link>
          </Button>
        </div>

        {/* Dashboard Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Profile Card */}
          <Card className="lg:col-span-1">
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <User className="h-6 w-6" />
                <span>Profile Information</span>
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div>
                <p className="text-sm text-gray-500">Full Name</p>
                <p className="font-medium">
                  {ownerProfile.firstName} {ownerProfile.lastName}
                </p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Email</p>
                <p className="font-medium">{ownerProfile.email}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Phone</p>
                <p className="font-medium">{ownerProfile.phoneNumber || "-"}</p>
              </div>
            </CardContent>
          </Card>

          {/* Shops Section */}
          <div className="lg:col-span-2">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-xl font-semibold">Your Shops</h2>
              <Button asChild>
                <Link href="/shops/create">
                  <PlusCircle className="mr-2 h-4 w-4" />
                  Add New Shop
                </Link>
              </Button>
            </div>

            {myShops.length === 0 ? (
              <div className="text-center py-12 bg-white rounded-lg">
                <p className="text-gray-500">No shops found</p>
                <Button variant="link" className="mt-4" asChild>
                  <Link href="/shops/create">Create your first shop</Link>
                </Button>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {myShops.map((shop) => (
                  <Card key={shop.id} className="hover:shadow-lg transition-shadow">
                    <CardHeader className="pb-2">
                      <div className="flex justify-between items-start">
                        <CardTitle className="text-lg">{shop.name}</CardTitle>
                        <Badge variant="outline">
                          {shop.seats?.length || 0} seats
                        </Badge>
                      </div>
                    </CardHeader>
                    <CardContent>
                      <p className="text-sm text-gray-600 mb-4">{shop.address}</p>
                      <div className="flex gap-2">
                        <Button variant="outline" size="sm" asChild>
                          <Link href={`/shops/${shop.id}`}>View Details</Link>
                        </Button>
                        <Button variant="outline" size="sm" asChild>
                          <Link href={`/shops/${shop.id}/edit`}>Edit Shop</Link>
                        </Button>
                      </div>
                    </CardContent>
                  </Card>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}