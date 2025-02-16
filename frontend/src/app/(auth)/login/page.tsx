// @app/(auth)/login/page.tsx

"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { useAuthStore } from "@/stores/auth.store";
import { authService } from "@/services/auth.service";
import {
  loginSchema,
  type LoginPayload,
} from "@/types/auth.types";
import { useState } from "react";

export default function LoginPage() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    resetField, // clear password only
    setError, // manual error handling
  } = useForm<LoginPayload>({
    resolver: zodResolver(loginSchema),
  });
  const { setCredentials } = useAuthStore();
  const router = useRouter();
  const [apiError, setApiError] = useState<string | null>(null);


  const onSubmit = async (data: LoginPayload) => {
    try {
      setApiError(null); // Reset error state

      const response = await authService.login(data);

      // Because response.id, response.email, etc. are top-level:
      const user = {
        id: response.id.toString(),
        username: response.username,
        // password: response.password, -- never store passwords in state
        email: response.email,
        roles: response.roles, // or transform these if needed
      };

      // Then store them
      setCredentials(user, response.token);

      // re-direct to da dashboard
      router.push(`/customer/${response.id}/dashboard`);
    } catch (error) {
      console.error("Login failed:", error); // console
      resetField("password"); // Clear only the password field

      // Set error specifically on password field with custom message
      setError("password", {
        type: "manual",
        message: "Invalid credentials",
      });
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Login</h2>

        {/* API Error Message */}
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
          {/* Combined error display for password */}
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