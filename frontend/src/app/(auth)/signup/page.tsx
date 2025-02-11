// @app/(auth)/signup/page.tsx

"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { authService } from "@/services/auth.service";
import { useAuthStore } from "@/stores/auth.store"; // ADDED MISSING IMPORT

import {
  registerSchema,
  type RegisterPayload,
  roles,
} from "@/types/auth.types";

export default function SignupPage() {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterPayload>({
    resolver: zodResolver(registerSchema),
    defaultValues: { role: "customer" },
  });
  const router = useRouter();
  const { setCredentials } = useAuthStore(); // ADDED STORE DESTRUCTURING -- IMPORTANT

  const onSubmit = async (data: RegisterPayload) => {
    try {
      // 1. Create user (REMOVED UNUSED AUTH RESPONSE)
      await authService.register(data);

      // 2. Log in with new credentials
      const loginResponse = await authService.login({
        username: data.username,
        password: data.password,
      });

      // 3. Set credentials in store
      setCredentials(
        {
          id: loginResponse.id.toString(),
          email: loginResponse.email,
          roles: loginResponse.roles,
        },
        loginResponse.token
      );

      // 4. Redirect to profile creation
      router.push("/profile/create");
    } catch (error) {
      console.error("Signup failed:", error);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Sign Up</h2>

        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Username</label>
          <input
            {...register("username")}
            className="w-full px-3 py-2 border rounded-md"
          />
          {errors.username && (
            <span className="text-red-500 text-sm">
              {errors.username.message}
            </span>
          )}
        </div>

        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Email</label>
          <input
            type="email"
            {...register("email")}
            className="w-full px-3 py-2 border rounded-md"
          />
          {errors.email && (
            <span className="text-red-500 text-sm">{errors.email.message}</span>
          )}
        </div>

        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Password</label>
          <input
            type="password"
            {...register("password")}
            className="w-full px-3 py-2 border rounded-md"
          />
          {errors.password && (
            <span className="text-red-500 text-sm">
              {errors.password.message}
            </span>
          )}
        </div>

        <div className="mb-6">
          <label className="block text-sm font-medium mb-1">Role</label>
          <select
            {...register("role")}
            className="w-full px-3 py-2 border rounded-md"
          >
            {roles.map((role) => (
              <option key={role} value={role}>
                {role.charAt(0).toUpperCase() + role.slice(1)}
              </option>
            ))}
          </select>
        </div>

        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700"
        >
          Create Account
        </button>
      </form>
    </div>
  );
}
