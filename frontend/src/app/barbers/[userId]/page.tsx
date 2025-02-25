"use client";
import { barberService } from "@/modules/barber/services";
import { BarberResponse } from "@/modules/barber/types/barber.types";
import { useAuthStore } from "@/stores/auth.store";
import { useQuery } from "@tanstack/react-query";
import { useParams, useRouter } from "next/navigation";


export default function BarberDashboard() {
  const params = useParams();
  const userId = params.userId as string; // Casting to a single string
  const router = useRouter();
  const { user } = useAuthStore();

  // Basic security check (optional):
  if (user && user.id !== userId) {
    router.push("/unauthorized");
  }

  const {
    data: barberProfile,
    isLoading,
    isError,
  } = useQuery<BarberResponse>({
    queryKey: ["barber-profile", userId],
    queryFn: () => barberService.getBarberProfile(userId),
    enabled: !!userId,
  });

  if (isLoading) return <div>Loading Barber Profile...</div>;
  if (isError || !barberProfile)
    return <div>Failed to load barber profile</div>;

  const handleEdit = () => {
    router.push(`/barbers/${userId}/profile/edit`);
  };

  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-4">Barber Dashboard</h1>

      <div className="bg-white p-6 rounded-md shadow-md w-[600px]">
        <h2 className="text-2xl mb-2">Your Profile</h2>
        <p>
          <strong>Full Name:</strong> {barberProfile.firstName}{" "}
          {barberProfile.lastName}
        </p>
        <p>
          <strong>Email:</strong> {barberProfile.email}
        </p>
        <p>
          <strong>Phone:</strong> {barberProfile.phoneNumber}
        </p>

        <button
          onClick={handleEdit}
          className="mt-4 px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
        >
          Edit Profile
        </button>
      </div>
    </div>
  );
}
