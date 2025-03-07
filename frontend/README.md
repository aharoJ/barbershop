# BarberOS Frontend 🖥️

**Next.js 15 • TypeScript • Tailwind CSS**

[![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)](https://typescriptlang.org)  
[![Zustand](https://img.shields.io/badge/State_Zustand-4.4.1-2A2A2A)](https://zustand-demo.pmnd.rs/)



[![Next.js](https://img.shields.io/badge/Next.js-15-000000?logo=next.js)](https://nextjs.org/) [![React](https://img.shields.io/badge/React-19-61DAFB?logo=react)](https://react.dev) [![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)](https://www.typescriptlang.org/) [![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-06B6D4?logo=tailwindcss)](https://tailwindcss.com) [![Zustand](https://img.shields.io/badge/State_Management-Zustand_5-2A2A2A)](https://zustand-demo.pmnd.rs/) [![TanStack Query](https://img.shields.io/badge/Data_Fetching-React_Query_5-FF4154)](https://tanstack.com/query)


[![Framer Motion](https://img.shields.io/badge/Animations-Framer_Motion_12-0055FF)](https://www.framer.com/motion/)
[![Radix UI](https://img.shields.io/badge/Components-Radix_UI-161618)](https://www.radix-ui.com/)
[![React Hook Form](https://img.shields.io/badge/Forms-React_Hook_Form_7-EC5990)](https://react-hook-form.com/)
[![Turbopack](https://img.shields.io/badge/Bundler-Turbopack_✓-000000)](https://turbo.build/pack)
[![ESLint](https://img.shields.io/badge/Linting-ESLint_9-4B32C3?logo=eslint)](https://eslint.org)


## UI Features

- **Role-based Dashboards**  
  ![Dashboard Preview](../public/frontend-img/dashboard.png)

- **Authentication Flows**
  ```tsx
  // Protected route example
  const { user } = useAuthStore();
  return user ? <Dashboard /> : <Login />;
  ```

## 🚦 Routing System

| Path                    | Component            |
| ----------------------- | -------------------- |
| `/owner/[id]/dashboard` | Shop analytics       |
| `/barber/[id]/schedule` | Appointment calendar |

## 🧱 Project Structure

```bash
src/
├── app/          # App router
├── stores/       # Zustand state
│   └── authStore.ts
└── modules/
    ├── auth/     # Auth services
    └── payment/  # Stripe integration
```

[🔼 Back to Main README](../README.md)

---

---

---

---

---

---

# BarberShop Frontend

Next.js web application for barbershop management.

## Features

- Authentication flows (Login/Signup)
- Profile management for:
  - Barbers
  - Customers
  - Shop Owners
- Shop browsing and management interface
- Protected routes using JWT

## UI Sample

> Sign up

![alt text](../public/frontend-img/signup.png)

> Complete your Profile

![alt text](../public/frontend-img/complete-your-profile.png)

> Dynamic routing {id}/dashboard

![alt text](../public/frontend-img/dashboard.png)

## Project Structure

```bash
src/
├── app/ - Next.js page routes
├── modules/ - Feature modules
│   ├── auth/ - Authentication services
│   ├── barber/ - Barber-related logic
├── stores/ - Zustand state management
└── utils/ - Shared utilities
```
