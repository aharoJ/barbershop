# BarberOS Frontend

[![Next.js](https://img.shields.io/badge/Next.js-15-000000?logo=next.js)](https://nextjs.org/) [![React](https://img.shields.io/badge/React-19-61DAFB?logo=react)](https://react.dev) [![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)](https://www.typescriptlang.org/) [![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-06B6D4?logo=tailwindcss)](https://tailwindcss.com) [![Zustand](https://img.shields.io/badge/State_Management-Zustand_5-2A2A2A)](https://zustand-demo.pmnd.rs/) [![TanStack Query](https://img.shields.io/badge/Data_Fetching-React_Query_5-FF4154)](https://tanstack.com/query) [![Framer Motion](https://img.shields.io/badge/Animations-Framer_Motion_12-0055FF)](https://www.framer.com/motion/) [![Radix UI](https://img.shields.io/badge/Components-Radix_UI-161618)](https://www.radix-ui.com/) [![React Hook Form](https://img.shields.io/badge/Forms-React_Hook_Form_7-EC5990)](https://react-hook-form.com/) [![Turbopack](https://img.shields.io/badge/Bundler-Turbopack_✓-000000)](https://turbo.build/pack) [![ESLint](https://img.shields.io/badge/Linting-ESLint_9-4B32C3?logo=eslint)](https://eslint.org)


A **modern web interface** for managing barbershop operations, built with **Next.js 15** and optimized for **performance, scalability, and developer experience**.  


![alt text](../public/frontend-img/owner-dashboard.png)

## **Repository Overview**

```
.
├── backend/       # Spring Boot API (Authentication, Business Logic)
├── frontend/      # Next.js 15 Web App (UI & Client Interaction)
├── docs/          # Documentation, ADRs, Security Reports (private for now)
├── public/        # Static assets (images, icons, etc.)
```

### 📖 **Documentation & Reference**

📌 **[Backend Documentation](backend/README.md)**

- 48 REST endpoints
- 22 entity relationships
- 9 enum state machines

📌 **[Frontend Documentation](frontend/README.md)**

- 31 React components
- 8 Zustand stores
- 4 authentication workflows


## **Frontend Architecture Overview**  

```mermaid
graph LR
  A[Client] -->|HTTP| B[Next.js Frontend]
  B -->|REST API| C[Spring Boot API]
  C -->|Database Queries| D[(PostgreSQL)]
  B -->|State Management| E[Zustand Store]
  B -->|Data Fetching| F[TanStack Query]
  B -->|Authentication| G[OAuth2 + JWT]
```

- **Next.js App Router** powers the client-side rendering and SSR.  
- **State management via Zustand** for global store handling.  
- **React Query handles data fetching**, caching, and synchronization.  
- **Secure authentication** with JWT tokens and OAuth2 integration.  

---

## **🏗 Component Architecture**  

```mermaid
graph TD
  A[Pages] --> B[UI Components]
  A --> C[Forms]
  A --> D[State Management]
  B --> E[Radix UI]
  C --> F[React Hook Form + Zod]
  D --> G[Zustand Store]
```

- **Pages:** Core Next.js page routes.  
- **UI Components:** Built using Radix UI primitives and Tailwind CSS.  
- **Forms:** Managed with React Hook Form and validated with Zod.  
- **State Management:** Centralized logic in Zustand stores.  

---

## **Project Structure**  

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

- **Feature Modules:** Encapsulate functionality for authentication, barbers, and shops.  
- **Zustand Stores:** Handle global application state.  
- **Shared Utilities:** Common functions for API calls and error handling.  

---

## **Role-Based Access Control**  

```mermaid
graph TD
  subgraph "User Roles & Permissions"
    A[Shop Owners] -->|Manage| B[Multi-location Shops]
    A -->|Track| C[Analytics & Staffing]

    D[Barbers] -->|Manage| E[Schedule & Clients]
    
    F[Customers] -->|Book| G[Appointments]
    F -->|View| H[Payment History]
  end
```

| **Role**      | **Capabilities**                           |
|--------------|------------------------------------------|
| **Owners**   | Multi-location management, analytics, staffing |
| **Barbers**  | Schedule management, client history     |
| **Customers**| Appointment booking, payment history    |

- **Owners** oversee **multiple shops** and handle **staffing**.  
- **Barbers** manage **client appointments and personal earnings**.  
- **Customers** book services and **view their payment history**.  

---

## **Authentication Flow**  

```mermaid
sequenceDiagram
    Client->>+Next.js Frontend: Login Request
    Next.js Frontend->>+Auth API: Authenticate Credentials
    Auth API-->>-Next.js Frontend: JWT Token
    Next.js Frontend->>+Protected Route: Forward Request (With JWT)
    Protected Route->>+Zustand Store: Retrieve User Data
    Zustand Store-->>-Next.js Frontend: User State Updated
```

- **Login Flow:** Users authenticate via the Next.js frontend, which requests a **JWT token** from the backend.  
- **State Management:** The JWT token is stored in **Zustand**, and **React Query synchronizes** user sessions.  
- **Protected Routes:** Pages enforce **role-based access control (RBAC)**.  

---

## **Routing Structure**  

```mermaid
graph TD
  A["/owner/{id}/dashboard"] -->|Authenticated| B["Owner Analytics"]
  C["/barber/{id}/schedule"] -->|Authenticated| D["Appointment Calendar"]
  E["/shops/{shopId}/seats"] -->|Owner Access| F["Seat Management"]
  G["/profile/dashboard"] -->|All Roles| H["Profile Dashboard"]
  I["/profile/edit"] -->|All Roles| J["Profile Editor"]
```

| **Route Pattern**       | **Component**           | **Access Level**  |
|------------------------|-----------------------|------------------|
| `/owner/[id]/dashboard` | Owner Analytics       | Authenticated    |
| `/barber/[id]/schedule` | Appointment Calendar  | Barber Role      |
| `/shops/[shopId]/seats` | Seat Management       | Shop Owner       |
| `/profile/dashboard`    | Profile Dashboard     | All Roles        |
| `/profile/edit`         | Profile Editor        | All Roles        |



## **License & Contribution**  

- **Licensed under AGPL-3.0** – See [LICENSE](LICENSE).  
- **Contributions Welcome** – Check [CONTRIBUTING.md](docs/CONTRIBUTING.md).  


## End
[View Backend Documentation](../backend/README.md)  
[View Backend Documentation](../frontend/README.md)  
[Main Project Documentation](../README.md)
