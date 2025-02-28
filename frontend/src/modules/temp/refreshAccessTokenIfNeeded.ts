import { authService } from "@/modules/auth/services/auth.service";
import { useAuthStore } from "@/stores/auth.store";

// export async function refreshAccessTokenIfNeeded() {
//   const { refreshToken, setCredentials, user } = useAuthStore.getState();
//   if (!refreshToken) return; // no refresh token means can't refresh

//   // call the backend
//   try {
//     const res = await authService.refresh({ refreshToken });
//     // store new token
//     setCredentials(user!, res.accessToken, res.refreshToken);
//   } catch (error) {
//     console.error("Failed to refresh token:", error);
//   }
// }
