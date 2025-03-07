# BarberOS Backend System

**Enterprise-grade barbershop management API**  
_Spring Boot 3 | Java 17 | PostgreSQL | JWT/RSA Security_

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot) [![Spring Security](https://img.shields.io/badge/Spring_Security-6-6DB33F?logo=springsecurity)](https://spring.io/projects/spring-security) [![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?logo=hibernate)](https://hibernate.org) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql)](https://www.postgresql.org/) [![JWT](https://img.shields.io/badge/JWT-0.11.5-000000?logo=jsonwebtokens)](https://jwt.io) [![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk)](https://openjdk.org) [![Actuator](https://img.shields.io/badge/Spring_Actuator-3-6DB33F)]() [![Validation](https://img.shields.io/badge/Bean_Validation-3-6DB33F)]()

## Architectural Overview

### Layered Architecture

```
Presentation Layer → Business Layer → Persistence Layer → Database
       ↑                   ↑                  ↑
    Controllers        Services         Repositories
```

### Security Architecture

```mermaid
sequenceDiagram
    Client->>+API Gateway: Request (No JWT)
    API Gateway->>+Auth Service: Authenticate
    Auth Service-->>-API Gateway: JWT Token
    API Gateway->>+Service Layer: Forward Request (With JWT)
    Service Layer->>+Persistence Layer: Data Operations
```

## Core Components

### 1. Authentication System

- **JWT Implementation**: RSA-256 signed tokens with refresh token rotation
- **Security Chain**:
  1. JwtRequestFilter validates token structure
  2. UserDetailsService loads user authorities
  3. SecurityContextHolder establishes execution context
- **Password Encoding**: BCrypt with strength 12

### 2. Domain Model

| Entity            | Relationships           | Business Rules              |
| ----------------- | ----------------------- | --------------------------- |
| `Appointment`     | ManyToOne: User, Barber | Time slot validation        |
| `Payment`         | OneToOne: Appointment   | PCI-DSS compliance          |
| `ShopApplication` | ManyToOne: Barber, Shop | State transition validation |

### 3. API Design Principles

- **Resource Naming**:
  - `/api/{domain}/{resource}/{id}`
  - Example: `/api/shops/{shopId}/seats`
- **Response Standardization**:
  ```json
  {
    "timestamp": "2024-02-20T14:23:38Z",
    "status": 200,
    "data": {},
    "errors": []
  }
  ```

## Module Snippet

### Shop Management Module

```java
// Seat allocation algorithm
public Seat allocateSeat(SeatRequest request) {
    validateMaxOccupancy(request.getShopId());
    checkBarberAvailability(request.getBarberId());
    return seatRepository.save(new Seat(...));
}
```

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

### Minimal Tree 

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

### Database Schema Management

```sql
CREATE TABLE shops (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    capacity INT CHECK (capacity > 0),
    CONSTRAINT unique_shop_name UNIQUE (name)
);
```

_Licensed under AGPL-3.0 - See [LICENSE](LICENSE) for details_  
_Architectural documentation maintained in `/docs/architecture`_

[View Frontend Documentation](../frontend/README.md)  
[Main Project Documentation](../README.md)
