
### **Frontend README.md** (copy-paste)  
```markdown
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

## 🚦 Routing System  
| Path                  | Component              |  
|-----------------------|------------------------|  
| `/owner/[id]/dashboard` | Shop analytics        |  
| `/barber/[id]/schedule` | Appointment calendar  |  

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
