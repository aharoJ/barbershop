# Barbershop Frontend

[![Next.js](https://img.shields.io/badge/Next.js-15-000000?logo=next.js)](https://nextjs.org/) [![React](https://img.shields.io/badge/React-19-61DAFB?logo=react)](https://react.dev) [![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)](https://www.typescriptlang.org/) [![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-06B6D4?logo=tailwindcss)](https://tailwindcss.com) [![Zustand](https://img.shields.io/badge/State_Management-Zustand_5-2A2A2A)](https://zustand-demo.pmnd.rs/) [![TanStack Query](https://img.shields.io/badge/Data_Fetching-React_Query_5-FF4154)](https://tanstack.com/query) [![Framer Motion](https://img.shields.io/badge/Animations-Framer_Motion_12-0055FF)](https://www.framer.com/motion/) [![Radix UI](https://img.shields.io/badge/Components-Radix_UI-161618)](https://www.radix-ui.com/) [![React Hook Form](https://img.shields.io/badge/Forms-React_Hook_Form_7-EC5990)](https://react-hook-form.com/) [![Turbopack](https://img.shields.io/badge/Bundler-Turbopack_✓-000000)](https://turbo.build/pack) [![ESLint](https://img.shields.io/badge/Linting-ESLint_9-4B32C3?logo=eslint)](https://eslint.org)

A **modern web interface** for managing barbershop operations, built with **Next.js 15** and optimized for **performance, scalability, and developer experience**.

![alt text](../public/frontend-img/owner-dashboard.png)

---

## 📖 Table of Contents

- [Introduction](#introduction)
- [Repository Overview](#repository-overview)
- [Documentation & Reference](#documentation--reference)
- [Frontend Architecture Overview](#frontend-architecture-overview)
  - [High-Level Architecture](#high-level-architecture)
  - [Component Architecture](#component-architecture)
  - [Role-Based Access Control](#role-based-access-control)
  - [Authentication Flow](#authentication-flow)
- [Project Structure](#project-structure)
- [Routing Structure](#routing-structure)
- [License & Contribution](#license--contribution)

---

## Introduction

The Barbershop Frontend is a performant, scalable, and developer-friendly web application built with Next.js and modern technologies, designed to simplify and optimize barbershop operations.

---

## Repository Overview

```
.
├── backend/       # Spring Boot API (Authentication, Business Logic)
├── frontend/      # (CURRENTLY HERE)
├── docs/          # Documentation, ADRs, Security Reports (private)
├── public/        # Static assets (images, icons, etc.)
```

---

## 📚 Documentation & Reference

- 📌 **[Landing Documentation](../README.md)**
- 📌 **[Backend Documentation](../backend/README.md)**
  - 48 REST endpoints
  - 22 entity relationships
  - 9 enum state machines
- 📌 **Frontend Documentation (Currently Viewing)**
  - 31 React components
  - 8 Zustand stores
  - 4 authentication workflows

---

## Frontend Architecture Overview

### High-Level Architecture

```mermaid
graph LR
  Client --> Next.js_Frontend
  Next.js_Frontend -->|REST API| Spring_Boot_API
  Spring_Boot_API -->|Queries| PostgreSQL_DB
  Next.js_Frontend --> Zustand_Store
  Next.js_Frontend -->|Data Fetching| React_Query
  Next.js_Frontend -->|Auth| OAuth2_JWT
```

- **Next.js App Router**: Handles SSR and dynamic routing.
- **State Management (Zustand)**: Simplified global state management.
- **Data Fetching (TanStack Query)**: Efficient data handling and caching.
- **JWT/OAuth2 Authentication**: Secure user authentication and sessions.

### Component Architecture

```mermaid
graph TD
  Pages --> UI_Components
  Pages --> Forms
  Pages --> State_Management
  UI_Components --> Radix_UI
  Forms --> React_Hook_Form
  State_Management --> Zustand
```

- **Pages**: Core navigational routes and entry points.
- **Components**: Built with Radix UI primitives and Tailwind CSS.
- **Forms**: Managed with React Hook Form and validated via Zod.
- **State Management**: Zustand stores manage global app states.

### Role-Based Access Control

```mermaid
graph TD
  Owners -->|Manage| Shops
  Owners -->|Track| Analytics
  Barbers -->|Manage| Schedule
  Customers -->|Book| Appointments
```

| Role      | Capabilities                               |
| --------- | ------------------------------------------ |
| Owners    | Multi-shop management, staffing, analytics |
| Barbers   | Scheduling, client management              |
| Customers | Appointment booking, payment overview      |

### Authentication Flow

```mermaid
sequenceDiagram
Client->>Frontend: Login Request
Frontend->>Auth_API: Authenticate
Auth_API-->>Frontend: JWT Token
Frontend->>Protected_Route: JWT Authenticated Request
Protected_Route->>Zustand: Update State
Zustand-->>Frontend: State Updated
```

---

## Project Structure

```
src/
├── app/            # Next.js routes
│   ├── (auth)/     # Authentication routes
│   ├── barbers/    # Barber-specific pages
│   ├── owners/     # Owner dashboards
│   └── shops/      # Shop management
├── modules/        # Feature-specific logic
├── stores/         # Zustand global stores
├── lib/            # Shared utilities
└── types/          # TypeScript interfaces
```

---

## Routing Structure

```mermaid
graph TD
  A["/owner/{id}/dashboard"] -->|Authenticated| B["Owner Analytics"]
  C["/barber/{id}/schedule"] -->|Authenticated| D["Appointment Calendar"]
  E["/shops/{shopId}/seats"] -->|Owner Access| F["Seat Management"]
  G["/profile/dashboard"] -->|All Roles| H["Profile Dashboard"]
  I["/profile/edit"] -->|All Roles| J["Profile Editor"]
```

| Route                   | Component         | Access Role |
| ----------------------- | ----------------- | ----------- |
| `/owner/[id]/dashboard` | Owner Dashboard   | Owners      |
| `/barber/[id]/schedule` | Schedule Calendar | Barbers     |
| `/shops/[shopId]/seats` | Seat Management   | Owners      |
| `/profile/dashboard`    | User Dashboard    | All Users   |
| `/profile/edit`         | Profile Editor    | All Users   |

---

## License & Contribution

Licensed under AGPL-3.0 – See [LICENSE](LICENSE).

---

### [Backend Documentation](../backend/README.md)

### [Landing Documentation](../README.md)
