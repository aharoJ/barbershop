// @app/(auth)/login/page.tsx

"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useRouter } from "next/navigation";
import { useAuthStore } from "@/stores/auth.store";
import { authService } from "@/services/auth.service";
import { loginSchema, RegisterPayload, type LoginPayload } from "@/types/auth.types";

export default function LoginPage() {
  const { register, handleSubmit } = useForm<LoginPayload>({
    resolver: zodResolver(loginSchema),
  });
  const { setCredentials } = useAuthStore();
  const router = useRouter();

  // login/page.tsx
  const onSubmit = async (data: LoginPayload) => {
    try {
      const response = await authService.login(data);

      // Because response.id, response.email, etc. are top-level:
    const user = {
      id: response.id.toString(),
      username: response.username,
      email: response.email,
      roles: response.roles,  // or transform these if needed
    };


      // Then store them
      setCredentials(user, response.token);

      // Push to the user’s ID
      // router.push(/customer/${response.id}/dashboard);
      router.push(`/customer/${response.id}/profile/create`);

      // router.push(/customer/${loginResponse.id}/profile/create);
    } catch (error) {
      console.error("Login failed:", error);
    }
  };
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="p-6 bg-white rounded-lg shadow-md w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Login</h2>

        <div className="mb-4">
          <label className="block text-sm font-medium mb-1">Username</label>
          <input
            {...register("username")}
            className="w-full px-3 py-2 border rounded-md"
          />
        </div>

        <div className="mb-6">
          <label className="block text-sm font-medium mb-1">Password</label>
          <input
            type="password"
            {...register("password")}
            className="w-full px-3 py-2 border rounded-md"
          />
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
