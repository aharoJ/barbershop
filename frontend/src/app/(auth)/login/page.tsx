// @app/(auth)/login/page.tsx

"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { useAuthStore } from "@/stores/auth.store";
import { authService } from "@/modules/auth/services/auth.service";
import { loginSchema, type LoginPayload } from "@/modules/auth/types/auth.types";
import { useState } from "react";

export default function LoginPage() {
  const router = useRouter();
  const { setCredentials } = useAuthStore();

  const {
    register,
    handleSubmit,
    formState: { errors },
    resetField,
    setError,
  } = useForm<LoginPayload>({ resolver: zodResolver(loginSchema) });

  const [apiError, setApiError] = useState<string | null>(null);

  const onSubmit = async (data: LoginPayload) => {
    try {
      setApiError(null);
      const response = await authService.login(data);

      setCredentials(
        {
          id: response.id.toString(),
          username: response.username,
          email: response.email,
          roles: response.roles,
        },
        response.token
      );

      // dynamic roles 
      if (response.roles.includes("ROLE_OWNER")) {
        router.push(`/owners/${response.id}`);
      } 
      else if (response.roles.includes("ROLE_CUSTOMER")) {
        router.push(`/customers/${response.id}`);
      } 
      else if (response.roles.includes("ROLE_BARBER")) {
        router.push(`/barbers/${response.id}`);
      } else {
        router.push("/"); // fallback
      }
    } catch (error) {
      console.error("Login failed:", error);
      resetField("password");
      setError("password", {
        type: "manual",
        message: "Invalid credentials",
      });
      setApiError("Login failed. Please check your credentials.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-[400px]"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Login</h2>

        {apiError && (
          <div className="mb-4 p-2 bg-red-100 text-red-700 text-sm rounded-md text-center">
            {apiError}
          </div>
        )}

        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Username</label>
          <input
            type="text"
            {...register("username")}
            className="w-full px-3 py-2 border rounded-md"
          />
          {errors.username && (
            <span className="text-red-500 text-sm">
              {errors.username.message}
            </span>
          )}
        </div>

        <div className="mb-6">
          <label className="block text-sm font-medium mb-1">Password</label>
          <input
            type="password"
            {...register("password")}
            className="w-full px-3 py-2 border rounded-md"
          />
          {errors.password && (
            <span className="text-red-500 text-sm block mt-1">
              {errors.password.message}
            </span>
          )}
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Sign In
        </button>
      </form>
    </div>
  );
}