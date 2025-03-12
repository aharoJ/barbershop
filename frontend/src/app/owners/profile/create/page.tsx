"use client";
// app/owners/profile/createpage.tx
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  ownerSchema,
  type OwnerPayload,
} from "@/modules/owner/types/owner.types";
import { useRouter } from "next/navigation";
import { ownerService } from "@/modules/owner/services";
import { useState } from "react";
import { imageService } from "@/modules/image/services";

export default function CreateOwnerProfilePage() {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<OwnerPayload>({
    resolver: zodResolver(ownerSchema),
  });

  // For storing the selected image file before upload
  const [selectedFile, setSelectedFile] = useState<File | null>(null);

  // 1) user picks a file
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setSelectedFile(e.target.files[0]);
    }
  };
  
  // 2) onSubmit → create the profile, then upload file, then go to dashboard
  const onSubmit = async (payload: OwnerPayload) => {
    try {
      // Create the owner profile first
      const newProfile = await ownerService.createOwnerProfile(payload);
      // newProfile.ownerId is e.g. 1

      // If the user selected a file, upload it
      if (selectedFile) {
        await imageService.uploadOwnerImage(newProfile.ownerId, selectedFile);
      }

      // Finally, go to the dashboard
      router.push("/owners/dashboard");
    } catch (error) {
      console.error("Create Owner Profile error:", error);
      alert("Failed to create profile or upload image.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-[500px]"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">
          Create Owner Profile
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

        {/* File input for image */}
        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">
            Profile Image
          </label>
          <input
            type="file"
            accept="image/*"
            onChange={handleFileChange}
            className="w-full"
          />
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Create Profile
        </button>
      </form>
    </div>
  );
}
