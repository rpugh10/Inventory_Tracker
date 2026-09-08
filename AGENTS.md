Project Overview

A real-time inventory tracking system for small retailers/restaurants. Tracks stock levels across multiple locations, records every stock movement as a ledger entry, computes usage-rate forecasts, and auto-generates reorder alerts when stock is predicted to run low. Built as a resume project to demonstrate backend architecture beyond basic CRUD.

Package root: com.example.inventoryTracker

Tech Stack
Spring Boot 3.x, Java 17+
Spring Web — REST API layer
Spring Data JPA + Hibernate — ORM
PostgreSQL — primary database
Flyway — schema migrations (ddl-auto is OFF; all schema changes go through versioned migration files in src/main/resources/db/migration/)
Spring Security + JWT — stateless auth, role-based access (ADMIN / STAFF)
Spring Batch — nightly job to recompute usage rates and generate reorder alerts
WebSockets (STOMP/SockJS) — push real-time alerts to a dashboard
Redis — cache current stock levels for fast dashboard reads
Lombok — reduce entity/DTO boilerplate (@Data, @NoArgsConstructor, @AllArgsConstructor)
springdoc-openapi — Swagger UI for API docs
JUnit 5 + Mockito — unit tests
Testcontainers — integration tests against real Postgres
Docker + Docker Compose — app + Postgres + Redis


Schema (finalized)
Location
PK id (Long, IDENTITY)
address (varchar 255)
Relationship: one Location → many StockLevel
Supplier
PK id (Long, IDENTITY)
supplierName (varchar 255)
email (varchar 255)
phoneNumber (varchar 20)
Relationship: one Supplier → many Transaction
User
PK id (Long, IDENTITY)
userName (varchar 255)
password (varchar 255, BCrypt-hashed, never plaintext)
role (varchar 50 — maps to Spring Security authority, e.g. ROLE_ADMIN / ROLE_STAFF)
Relationship: one User → many Transaction
Product
PK id (Long, IDENTITY)
productName (varchar 255)
category (varchar 255)
unit (varchar 255)
sku (varchar 255, unique business key)
reorderThreshold (int — used by Spring Batch alert job)
unitCost (decimal)
Relationship: one Product → many StockLevel, one Product → many Transaction
StockLevel (current quantity snapshot, per product per location)
PK id (Long, IDENTITY — surrogate key)
product (ManyToOne → Product, @JoinColumn(name = "productID"))
location (ManyToOne → Location, @JoinColumn(name = "locationID"))
quantity (int)
lastUpdated (timestamp)
Constraint (TODO — not yet enforced in code): unique composite constraint on (product, location) — a product must have exactly one StockLevel row per location. Use @Table(uniqueConstraints = ...) at the class level.
Transaction (ledger — source of truth for all stock movement)
PK id (Long, IDENTITY)
user (ManyToOne → User, @JoinColumn(name = "userID"))
supplier (ManyToOne → Supplier, @JoinColumn(name = "supplierID"))
product (ManyToOne → Product, @JoinColumn(name = "productID"))
location (ManyToOne → Location, @JoinColumn(name = "locationID"))
quantity (int)
transactionType (currently String — TODO: convert to enum STOCK_IN / STOCK_OUT / ADJUSTMENT)
transactionDate (timestamp)
note (varchar 255, optional)

Relationship rule applied throughout: the FK always lives on the "many" side. Transaction is the "many" side of every one of its relationships (User, Supplier, Product, Location all point to Transaction conceptually, so Transaction holds all four FKs). StockLevel is the "many" side relative to both Product and Location.

Entity Code Conventions (established so far)
Field holding a related entity is named after the entity, not the FK column (private Product product;, NOT private Product productID;). The FK column name is set via @JoinColumn(name = "productID").
@ManyToOne used for every FK relationship in Transaction and StockLevel (never @OneToMany on a scalar/ID field — that annotation is only for collection fields on the "one" side).
Fixed-value fields (like transactionType) should eventually become Java enum types mapped with JPA, not raw Strings — avoids inconsistent values like "stockin" vs "STOCK_IN".
DTOs should be used at the API boundary — don't expose @Entity classes directly in controllers.
Schema changes go through Flyway migrations, not ddl-auto: update.
Migration Order (dependency-respecting)
V1__create_locations_table.sql
V2__create_suppliers_table.sql
V3__create_users_table.sql
V4__create_products_table.sql
V5__create_stock_levels_table.sql (depends on products, locations)
V6__create_transactions_table.sql (depends on products, locations, users, suppliers)


Milestones
1. Project setup & core entities — ✅ Done Spring Initializr scaffold, schema design finalized (Location, Supplier, User, Product, StockLevel, Transaction), Flyway set up, entities written for Transaction and StockLevel with correct @ManyToOne relationships. Remaining cleanup: rename entity fields off the xID naming pattern, add unique composite constraint to StockLevel, convert transactionType to an enum, write Product/User/Supplier/Location entities using the same pattern, build the first CRUD vertical slice (Product: repository → service → controller → DTOs).

2. Authentication & authorization — 🔲 In progress Spring Security + JWT. Two roles: ADMIN (manage products/suppliers, view all data) and STAFF (record stock movements only). Steps: add security + JWT dependencies → adapt User entity to UserDetails → add PasswordEncoder (BCrypt) → build UserDetailsService → build JWT utility (generate/validate/extract claims) → build /auth/login endpoint → build custom JWT filter (OncePerRequestFilter) → wire up SecurityFilterChain (stateless sessions, CSRF disabled, public vs protected routes, role restrictions). Open task: sketch an endpoint-to-role table before writing the filter chain config.

3. Stock movement tracking — 🔲 Not started Endpoints to record stock in/out. Every movement writes a Transaction row and updates the matching StockLevel row (matched on product + location).

4. Usage rate calculation & forecasting — 🔲 Not started Service to compute average daily usage from Transaction history (moving average over last N days) and days-until-stockout (current_stock / avg_daily_usage).
Spring Batch job for reorder alerts — 🔲 Not started Nightly job: compute days-until-stockout per product, create a ReorderAlert record with suggested reorder quantity when below threshold.

5. Real-time dashboard with WebSockets — 🔲 Not started Push new ReorderAlerts (and critical stock drops) to connected clients via STOMP/SockJS. Minimal frontend to demo live alerts.

6. Caching layer — 🔲 Not started Redis cache for current stock level reads; invalidate/update on each transaction.

7. Testing — 🔲 Not started Unit tests for forecasting logic and batch job. Testcontainers integration tests for repository layer and key API flows.

8. API documentation — 🔲 Not started springdoc-openapi, /swagger-ui.html.

9. Containerization & deployment — 🔲 Not started Dockerfile + docker-compose.yml (app, Postgres, Redis). Deploy to Railway/Render/AWS free tier.

10. Polish for resume presentation — 🔲 Not started README with architecture diagram, problem statement, tech-decision rationale, screenshots/GIF, live demo link.


Open Questions / Decisions Made
Supplier is linked only via Transaction (not directly to Product) — a product's "supplier" is derived from transaction history rather than a fixed field. This is intentional (products can be sourced from different suppliers over time).
SKU is kept as a unique business key on Product, not the primary key (surrogate id is the PK instead).
StockLevel uses a surrogate id PK rather than a true composite (productID, locationID) PK — uniqueness on that pair still needs to be enforced via a @Table(uniqueConstraints = ...) constraint (not yet added).