# CLAUDE.md — BarberOS

## What This Is

BarberOS is a "Yelp for barbershops" B2B2C SaaS platform — complete rewrite from scratch. The old code in this repo is dead; it will be gutted and replaced. The concept is validated (hundreds to thousands of GitHub views/month, steady stars), the code is not.

**3 research phases are complete** (15 architecture decisions locked across 3 rounds of adversarial cross-review with 5 independent LLMs). All decisions, constraints, and architecture blueprints live in `~/.notes/projects/barberos/`.

## Critical Files (Read These First)

| File | What it contains |
|------|-----------------|
| `~/.notes/projects/barberos/runbook/rewrite.md` | **Full architecture blueprint** — module structure, database schema, auth flows, scheduling design, /ee extension points, build order |
| `~/.notes/projects/barberos/decisions.md` | All 15 locked decisions (D1-D15) with rationale and model consensus |
| `~/.notes/projects/barberos/constraints.md` | 17 hard constraints — violating any of these contradicts cross-reviewed decisions |
| `~/.notes/projects/barberos/roadmap.md` | Phase tracker — Phases 1-3 (research) COMPLETE, Phase 4 (implementation) is current |
| `~/.notes/projects/barberos/runbook/launch.md` | Open-source launch runbook (repo structure, license files, CONTRIBUTING.md) |

## Engineering Baseline: Stage

The sibling project **Stage** (`~/.repository/stage/`) is the engineering reference. Every pattern below is proven in production there. Read Stage's `CLAUDE.md` and `GOTCHAS.md` for conventions and known pitfalls.

**Carry forward from Stage:**
- Spring Boot 4, Java 25, Gradle (not Maven)
- Spring Modulith (`internal/` packages for encapsulation, `ApplicationModules.verify()` in tests)
- PostgreSQL 17, Flyway migrations (no JPA auto-DDL, no H2)
- OAuth2/OIDC (Google + GitHub) via Spring Security sessions (no JWT)
- CSRF cookie pattern (`XSRF-TOKEN` cookie, `X-XSRF-TOKEN` header)
- Docker Compose (prod) + docker-compose.dev.yml (overlay), multi-stage Dockerfiles
- Taskfile for automation (`task dev`, `task test`, `task db`)
- UUID primary keys, `@GeneratedValue(strategy = GenerationType.UUID)`
- Records for DTOs (immutable, no setters)
- `ProblemDetail` for error responses via `GlobalExceptionHandler`
- `TransactionTemplate` for programmatic transaction control
- S3/MinIO storage abstraction
- Rate limiting (20 mutations/min per IP)
- Next.js App Router, React Server Components, Tailwind, minimal frontend deps
- `fetchAPIServer` (RSC with cookie forwarding) + `fetchAPI` (browser with CSRF)
- CSP with per-request nonces

## Locked Architecture Decisions (Summary)

### Open-Source Model (D1-D8)
- **D1**: AGPL-3.0 license (core). Permanent — never relicense.
- **D2**: `/ee` directory for enterprise code with proprietary `LICENSE_EE.md`
- **D3**: Simple `backend/` + `frontend/` monorepo structure
- **D4**: Fresh git start (single initial commit, no history preservation)
- **D5**: Build empty `/ee` scaffold before launch (dirs + LICENSE_EE + noop LicenseService)
- **D6**: Kill Copybara, archive old repos with redirect
- **D7**: No CLA, no DCO — implicit license via CONTRIBUTING.md
- **D8**: Keep existing `aharoJ/barbershop` repo, force-push fresh content

### Rewrite Architecture (D9-D15)
- **D9**: Auth = OAuth2 (Google + GitHub) + magic link. **No passwords ever.** No bcrypt, no reset flows.
- **D10**: Polymorphic profile + eliminate Owner role. Shared `Profile` + optional `BarberDetails`/`CustomerDetails`. Owner = `ShopMembership`, not a role. Multi-role users (barber + customer + shop admin simultaneously).
- **D11**: Range-based scheduling + PostgreSQL `EXCLUDE USING gist` for conflict prevention. Slots computed at query time. Optimistic locking via `@Version`.
- **D12**: Full Shop + Seat + BarberShopAssociation from day one. Independent barbers = null `shop_id`.
- **D13**: Shared tables, `shop_id` as primary data scope. No `tenant_id`. Self-hosted = shop management only, not discovery.
- **D14**: Spring Modulith + SPI hybrid for `/ee`. Core defines interfaces + noop defaults. `/ee` provides `@Primary` implementations gated by `@ConditionalOnProperty`.
- **D15**: Build order = Auth → Profile+Shop (parallel) → Service Catalog → Scheduling → Reviews → Payments → Platform.

## Key Constraints (Most Critical)

- **No passwords ever** (C11): Auth is OAuth2 + magic link only. Password auth is a `/ee` feature if ever needed.
- **Owner is not a role** (C12): Shop ownership is a `ShopMembership` relationship. Any user can create a shop.
- **Multi-role users are first-class** (C13): BarberDetails and CustomerDetails are optional 0..1 extensions, not mutually exclusive.
- **Double-booking prevention is database-level** (C14): PostgreSQL `EXCLUDE USING gist` constraint. Application checks are defense-in-depth.
- **Store times in UTC** (C16): All timestamps as `TIMESTAMPTZ`. Display in shop timezone. Critical for DST.
- **Service catalog before scheduling** (C17): Can't compute slots without service durations.
- **Quality over speed** — never cut corners, never defer quality for shipping velocity. Aim for top 0.1% engineering.

## Module Structure

```
com.barberos/
  auth/           # OAuth2 + magic link, session management
  identity/       # User, Profile, BarberDetails, CustomerDetails
  shop/           # Shop, Seat, ShopMembership, BarberShopAssociation
  catalog/        # Service menu (name, duration, buffer, price)
  scheduling/     # Availability templates, exceptions, appointments, state machine
  review/         # Reviews (attached to barber_id, follow across shops)
  payment/        # Core interfaces (noop default), /ee provides Stripe
  shared/         # GlobalExceptionHandler, RateLimitFilter, StorageService
  ee/             # Enterprise extensions (empty scaffold initially)
```

Each module uses Spring Modulith conventions: `internal/` packages are private to the module. Public API at module root. `ApplicationModules.verify()` enforces boundaries.

## Build Order

```
Phase 1: Auth (OAuth2 + magic link + sessions + CSRF)
Phase 2: Identity + Shop (parallel — no dependency between them)
Phase 3: Service Catalog (barber's menu — prerequisite for scheduling)
Phase 4: Scheduling (availability + slot computation + booking + state machine)
Phase 5: Reviews (post-appointment, attached to barber_id)
Phase 6: Payments (core interfaces, /ee Stripe implementation)
Phase 7: Platform (geo-search, discovery, ranking)
```

Each phase: implement → test → verify Modulith boundaries → proceed.

## Domain Context

**Users**: Barbers (manage schedule, build reputation), Shop Owners (manage locations + chairs + staff), Customers (discover, book, review).

**Key domain insights**:
- Barbers move between shops every 6-9 months — `BarberShopAssociation` with `joined_at`/`left_at` tracks career history
- Reviews follow the barber (linked by `barber_id`), not the shop tenure
- Seats are physical chairs — each seat = one barber's revenue stream
- Independent barbers operate without a shop (`shop_id = null`)
- Payment is fragmented IRL (cash, Cash App, Venmo) — unified tracking is the value prop
- "Yelp for barbershops" = centralized platform for discovery. Self-hosted = shop management only.

## Commit Convention

```
<phase>: <what changed>
```

Phases: `auth`, `identity`, `shop`, `catalog`, `scheduling`, `review`, `payment`, `platform`, `infra`, `meta`

Examples: `auth: magic link token generation + validation`, `scheduling: PostgreSQL exclusion constraint for appointments`

## What NOT to Do

- No H2 database, no in-memory anything
- No JWT (use Spring Security sessions like Stage)
- No Maven (use Gradle like Stage)
- No password fields, no bcrypt, no password reset flows
- No `OwnerProfile` entity — ownership is a `ShopMembership`
- No single role enum on User — use separate optional profile extensions
- No fixed time slots in the database — compute from availability ranges
- No `tenant_id` columns — use `shop_id` for data scoping
- No feature flag machinery in `/ee` scaffold — just empty dirs + LICENSE_EE + noop interfaces
- No shipping pressure — quality is the priority, not velocity
