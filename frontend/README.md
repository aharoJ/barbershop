# BarberOS Frontend

[![Next.js](https://img.shields.io/badge/Next.js-15-000000?logo=next.js)](https://nextjs.org/) [![React](https://img.shields.io/badge/React-19-61DAFB?logo=react)](https://react.dev) [![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)](https://www.typescriptlang.org/) [![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-06B6D4?logo=tailwindcss)](https://tailwindcss.com) [![Zustand](https://img.shields.io/badge/State_Management-Zustand_5-2A2A2A)](https://zustand-demo.pmnd.rs/) [![TanStack Query](https://img.shields.io/badge/Data_Fetching-React_Query_5-FF4154)](https://tanstack.com/query) [![Framer Motion](https://img.shields.io/badge/Animations-Framer_Motion_12-0055FF)](https://www.framer.com/motion/) [![Radix UI](https://img.shields.io/badge/Components-Radix_UI-161618)](https://www.radix-ui.com/) [![React Hook Form](https://img.shields.io/badge/Forms-React_Hook_Form_7-EC5990)](https://react-hook-form.com/) [![Turbopack](https://img.shields.io/badge/Bundler-Turbopack_✓-000000)](https://turbo.build/pack) [![ESLint](https://img.shields.io/badge/Linting-ESLint_9-4B32C3?logo=eslint)](https://eslint.org)

Modern web interface for barbershop management system built with Next.js App Router and Turbopack.

## Technical Architecture

**Core Stack:**

- **Framework**: Next.js 15 with App Router
- **State Management**: Zustand 5
- **Data Fetching**: TanStack Query v5
- **Form Handling**: React Hook Form 7 + Zod validation
- **Component Library**: Radix UI Primitives
- **Styling**: Tailwind CSS 3.4 with CSS Modules
- **Build System**: Turbopack

## System Features

### Role-Based Access Control

| User Role   | Capabilities                                   |
| ----------- | ---------------------------------------------- |
| Shop Owners | Multi-location management, analytics, staffing |
| Barbers     | Schedule management, client history            |
| Customers   | Appointment booking, payment history           |

### Key Functionality

- JWT-based authentication flow
- Dynamic route protection
- Real-time seat allocation system
- Shop management interface
- Profile CRUD operations
- Responsive mobile-first design

## Routing Structure

| Route Pattern           | Component            | Access Level  |
| ----------------------- | -------------------- | ------------- |
| `/owner/[id]/dashboard` | Owner Analytics      | Authenticated |
| `/barber/[id]/schedule` | Appointment Calendar | Barber Role   |
| `/shops/[shopId]/seats` | Seat Management      | Shop Owner    |
| `/profile/dashboard`    | Profile Dashboard    | All Roles     |
| `/profile/edit`         | Profile Editor       | All Roles     |
| `/profile/delete`       | Profile Delete       | All Roles     |
| `/profile/logout`       | Profile Logout       | All Roles     |

### Authentication Flow

```tsx
// ProtectedRoute.tsx
import { useAuthStore } from "@/stores/auth.store";

export default function ProtectedRoute({
  children,
}: {
  children: React.ReactNode;
}) {
  const { user, loading } = useAuthStore();

  if (loading) return <LoadingSpinner />;
  return user ? children : redirect("/login");
}
```

### Project Structure

```
src/
├── app/            # Next.js page routes
│   ├── (auth)      # Authentication routes
│   ├── barbers/    # Barber-specific views
│   ├── owners/     # Owner management
│   └── shops/      # Shop operations
├── modules/        # Feature modules
│   ├── auth/       # Authentication services
│   ├── barber/     # Barber management
│   └── shop/       # Shop operations
├── stores/         # Zustand state stores
├── lib/            # Shared utilities
└── types/          # TypeScript definitions
```

## Quality Assurance

- Type-safe development with TypeScript 5
- ESLint 9 with Next.js ruleset
- End-to-end type validation with Zod
- Automated code formatting with Prettier

[View Backend Documentation](../backend/README.md)  
[Main Project Documentation](../README.md)
