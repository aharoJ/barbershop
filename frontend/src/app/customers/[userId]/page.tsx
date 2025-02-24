"use client";

import { customerServiceImpl } from "@/modules/customer/services";
import { CustomerResponse } from "@/modules/customer/types/customer.types";
import { useQuery } from "@tanstack/react-query";
import { useParams, useRouter } from "next/navigation";

export default function CustomerDashboard() {
  const params = useParams();
  const router = useRouter();
  const userId = params.userId as string;

  const {
    data: customerProfile,
    isLoading,
    isError,
  } = useQuery<CustomerResponse>({
    queryKey: ["customer-profile"],
    queryFn: () => customerServiceImpl.getCustomerProfile(userId),
    enabled: !!userId, // only run if userId is defined
  });

  if (isLoading) return <div>Loading Customer Profile...</div>;
  if (isError || !customerProfile) return <div>Failed to load profile</div>;

  const handleEdit = () => {
    router.push(`/customers/${userId}/profile/edit`);
  };


  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-4">Customer Dashboard</h1>

      <div className="bg-white p-6 rounded-md shadow-md w-[600px]">
        <h2 className="text-2xl mb-2">Your Profile</h2>
        <p>
          <strong>Full Name:</strong> {customerProfile.firstName}{" "}
          {customerProfile.lastName}
        </p>
        <p>
          <strong>Email:</strong> {customerProfile.email}
        </p>
        <p>
          <strong>Phone:</strong> {customerProfile.phoneNumber}
        </p>
        <p>
          <strong>Date of Birth:</strong> {customerProfile.dateOfBirth}
        </p>
        <p className="mt-4 font-semibold">Address:</p>
        <p>
          {customerProfile.address?.street}, {customerProfile.address?.city},{" "}
          {customerProfile.address?.state} {customerProfile.address?.postalCode}
          , {customerProfile.address?.country}
        </p>

        <button
          onClick={handleEdit}
          className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          Edit Profile
        </button>
      </div>
    </div>
  );
}
