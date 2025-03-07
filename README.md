# BarberOS ✂️ - Modern Barbershop Management Platform

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?logo=spring)](https://spring.io/) [![Next.js](https://img.shields.io/badge/Next.js-15-000000?logo=next.js)](https://nextjs.org/) [![AGPL-3.0](https://img.shields.io/badge/License-AGPL--3.0-blue)](LICENSE) [![TypeScript](https://img.shields.io/badge/TypeScript-5-3178C6?logo=typescript)](https://www.typescriptlang.org/) [![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-06B6D4?logo=tailwindcss)](https://tailwindcss.com/) [![OAuth2](https://img.shields.io/badge/OAuth_2.0-✅-EB5424?logo=openid)](https://oauth.net/2/)

![Platform Overview](public/landing-img/fullstack-owner-dashboard.png)

**Enterprise-ready solution** connecting barbers, clients, and shop owners through digital workflows.

---

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

---

### **Key Features**

| Role        | Capabilities                                                                                 |
| ----------- | -------------------------------------------------------------------------------------------- |
| **Owners**  | 🏢 Multi-shop management • 📊 Analytics & Reporting • 👥 Staffing Control • 💰 Staff earning |
| **Barbers** | 📅 Schedule management • 🗂 Client history • 💰 Earnings tracking                             |
| **Clients** | 🔖 Booking • ⭐ Reviews & Feedback • 💳 Payment history                                      |

#### 🏗 Architecture

```mermaid
graph LR
  subgraph "System Architecture"
    A[Client] --> B[Next.js Frontend]
    B --> C[Spring Boot API]
    C --> D[(PostgreSQL Database)]
    C --> E[Redis Cache]
    C --> F[Payment Gateway: Stripe & PayPal]
    C --> G[Auth Service - OAuth2]
  end

  %% Dark Mode Styling
  classDef primary fill:black,stroke:white,color:white,font-weight:bold;
  classDef secondary fill:black,stroke:white,color:white;

  class A,B,C primary;
  class D,E,F,G secondary;
```

```mermaid
mindmap
  root((User Roles))
    Owners
      Multi-Shop Management
      Analytics & Insights
      Staffing & Operations
    Barbers
      Schedule Management
      Client History
      Earnings & Payouts
    Clients
      Booking Appointments
      Reviews & Feedback
      Payment History
```

---

## Citation & Attribution

```
@software{Barbershop,
  author = {Angel Jair Haro Jimenez},
  title = {Barbershop: Modern Barbershop Management Platform},
  year = {2025},
  url = {https://github.com/aharoj/barbershop}
}
```
