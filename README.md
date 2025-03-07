# BarberOS ✂️ - Modern Barbershop Management Platform

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?logo=spring)](https://spring.io/) [![Next.js](https://img.shields.io/badge/Next.js-15-000000?logo=next.js)](https://nextjs.org/) [![AGPL-3.0](https://img.shields.io/badge/License-AGPL--3.0-blue)](LICENSE)

![Platform Overview](public/landing-img/fullstack-owner-dashboard.png)

**Enterprise-ready solution** connecting barbers, clients, and shop owners through digital workflows.  
**Live Demo**: [demo.barberos.app](https://your-demo-link.com) | **Documentation**: [docs.barberos.app](https://your-docs-link.com)

---

## 🌟 Key Features

| Role        | Capabilities                                    |
| ----------- | ----------------------------------------------- |
| **Owners**  | Multi-shop management • Analytics • Staffing    |
| **Barbers** | Schedule management • Client history • Earnings |
| **Clients** | Booking • Reviews • Payment history             |

**Core Modules**:  
✅ Real-time seat allocation • 💳 Stripe/PayPal integration  
🔒 JWT+RSA security • 📈 Promotion engine • 📱 Mobile-first UI

---

## 🏗 Architecture

```mermaid
graph LR
  A[Client] --> B[Next.js Frontend]
  B --> C[Spring Boot API]
  C --> D[(PostgreSQL)]
  C --> E[Redis]
  C --> F[Payment Gateways]
```

---

## 🚀 Get Started

1. **Backend Setup**  
   [Detailed backend instructions →](backend/README.md)

   ```bash
   cd backend && mvn spring-boot:run
   ```

2. **Frontend Setup**  
   [Detailed frontend instructions →](frontend/README.md)
   ```bash
   cd frontend && npm install && npm run dev
   ```

---

## 📂 Repository Structure

```
.
├── backend/       # Spring Boot API • PostgreSQL • Security
├── frontend/      # Next.js 15 • Tailwind • Zustand
└── public/        # Assets & screenshots
```

---

### **Frontend README.md** (copy-paste)

````markdown
# BarberOS Frontend 🖥️

**Next.js 15 • TypeScript • Tailwind CSS**

[![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)](https://typescriptlang.org)  
[![Zustand](https://img.shields.io/badge/State_Zustand-4.4.1-2A2A2A)](https://zustand-demo.pmnd.rs/)

## 🌈 UI Features

- **Role-based Dashboards**  
  ![Dashboard Preview](../public/frontend-img/dashboard.png)

- **Authentication Flows**
  ```tsx
  // Protected route example
  const { user } = useAuthStore();
  return user ? <Dashboard /> : <Login />;
  ```
````

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

### [VISIT BACKEND](backend/)

Spring Boot API handling business logic, authentication, and data management

### [VISIT FRONTEND](frontend/)

Next.js web application implementing user workflows
