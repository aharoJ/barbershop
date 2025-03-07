# BarberOS Backend API 💻

**Spring Boot 3 • PostgreSQL • JWT/RSA Security**

[![Java 17](https://img.shields.io/badge/Java-17-007396?logo=java)](https://java.com/) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql)](https://www.postgresql.org/)

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot) [![Spring Security](https://img.shields.io/badge/Spring_Security-6-6DB33F?logo=springsecurity)](https://spring.io/projects/spring-security) [![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?logo=hibernate)](https://hibernate.org) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql)](https://www.postgresql.org/) [![JWT](https://img.shields.io/badge/JWT-0.11.5-000000?logo=jsonwebtokens)](https://jwt.io) [![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk)](https://openjdk.org) [![Actuator](https://img.shields.io/badge/Spring_Actuator-3-6DB33F)]() [![Validation](https://img.shields.io/badge/Bean_Validation-3-6DB33F)]()

## 🛠 Features

| Module       | Endpoints                    | Security          |
| ------------ | ---------------------------- | ----------------- |
| **Auth**     | `POST /api/auth/*`           | RSA-encrypted JWT |
| **Shops**    | `GET /api/shops` (public)    | RBAC-protected    |
| **Payments** | `POST /api/payments/process` | PCI-compliant     |

## 📦 Database Schema

![Database Diagram](../public/backend-img/database.png)

## 🧩 Project Structure

```bash
barber/
├── controller    # API endpoints
├── service       # Business logic
├── model         # JPA entities
└── security/     # Auth config
    ├── JwtTokenUtil.java
    └── WebSecurityConfig.java
```

[🔼 Back to Main README](../README.md)

---

---

---

---

---

---

# BarberShop Backend API

REST API for managing barbershop operations, built with Java Spring Boot.

## Features

### Authentication

- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login with JWT

### Profile Management

- **Barbers**: CRUD operations via `/api/barbers/me`
- **Customers**: CRUD operations via `/api/customers/me`
- **Owners**: CRUD operations via `/api/owners/me`

### Shop Management

- `GET /api/shops` - Public shop listing
- Owner endpoints for shop creation, seat management, and barber assignments

### Applications System

- Barber applications workflow with owner approval/rejection

## Image Samples

> function snippet

![alt text](../public/backend-img/reject-application.gif)

> Database sample

![alt text](../public/backend-img/database.png)

### Project Structure

> Auth and Barber minimal sample

```bash
.
├── auth
│  ├── config
│  ├── controller
│  │  └── test
│  ├── dto
│  │  ├── request
│  │  └── response
│  ├── exception
│  ├── model
│  │  ├── entity
│  │  └── enums
│  ├── repository
│  ├── security
├── barber
│  ├── controller
│  ├── dto
│  │  ├── request
│  │  └── response
│  ├── exception
│  ├── model
│  │  └── entity
│  ├── repository
│  ├── service
│  └── serviceImpl
```
