// @app/(auth)/signup/page.tsx

"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { authService } from "@/modules/auth/services/auth.service";
import { useAuthStore } from "@/stores/auth.store";
import {
  registerSchema,
  type RegisterPayload,
  roles,
} from "@/modules/auth/types/auth.types";
import { userService } from "@/modules/auth/services/user.service";

export default function SignupPage() {
  const router = useRouter();
  const { setCredentials } = useAuthStore();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<RegisterPayload>({
    resolver: zodResolver(registerSchema),
    defaultValues: { role: "customer" }, // "customer" | "barber" | "owner"
  });

  const onSubmit = async (data: RegisterPayload) => {
    try {
      // Sign up
      await userService.createUser(data);

      // Then log in automatically
      const loginRes = await authService.login({
        username: data.username,
        password: data.password,
      });

      setCredentials(
        {
          id: loginRes.id.toString(),
          username: loginRes.username,
          email: loginRes.email,
          roles: loginRes.roles,
        },
        loginRes.accessToken,
        loginRes.refreshToken,
      );

      if (loginRes.roles.includes("ROLE_OWNER")) {
        router.push(`/owners/profile/create`);
      } else if (loginRes.roles.includes("ROLE_CUSTOMER")) {
        router.push(`/customers/profile/create`);
      } else if (loginRes.roles.includes("ROLE_BARBER")) {
        router.push(`/barbers/profile/create`);
      } else {
        router.push("/");
      }
    } catch (error) {
      console.error("Signup errorRRRR:", error);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-[450px]"
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

        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Role</label>
          <select
            {...register("role")}
            className="w-full px-3 py-2 border rounded-md"
          >
            {roles.map((r) => (
              <option key={r} value={r}>
                {r}
              </option>
            ))}
          </select>
          {errors.role && (
            <span className="text-red-500 text-sm">{errors.role.message}</span>
          )}
        </div>

        <button className="w-full mt-4 bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700">
          Sign Up
        </button>
      </form>
    </div>
  );
}
