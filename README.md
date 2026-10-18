# Inventory Tracker

## Description

Inventory Tracker is a backend application for small retailers and restaurants that need to keep track of stock across multiple locations. It records incoming stock, outgoing stock, and inventory corrections, so you can see both what is on hand and how it got there.

I built this project to practice backend development beyond basic CRUD: authentication, role permissions, database migrations, and keeping stock balances consistent with a transaction ledger.

The project uses Java 17, Spring Boot, Spring Data JPA, PostgreSQL, Spring Security, and JWT. Flyway manages the database schema, MapStruct handles DTO mapping, and Swagger UI provides a way to explore the API.

## Features

- Manage products, locations, and suppliers through REST endpoints.
- Track stock separately for each product and location.
- Record `STOCK_IN`, `STOCK_OUT`, and `STOCK_ADJUSTMENT` movements. Each movement updates the stock balance and saves a ledger entry in the same database transaction.
- Reject outgoing movements when there is not enough stock available.
- Attach the signed-in user and a server-generated timestamp to each movement.
- Filter transaction history by product, location, and date range.
- Authenticate with JWTs and BCrypt-hashed passwords. Admins manage catalog data and users; both admins and staff can view inventory and record movements.
- Explore the API through Swagger UI, with Docker Compose available for running the application and PostgreSQL.
- Unit tests cover stock creation, stock deductions, insufficient stock, and preventing ledger writes when a stock movement fails.

Usage forecasting, automatic reorder alerts, a live dashboard, and Redis caching are planned additions.

## Installation

### Requirements

- JDK 17 or later to build the application.
- Docker and Docker Compose for the setup below.

Run these commands from the project root. The examples use PowerShell and the included Maven wrapper. On macOS or Linux, use `./mvnw` instead of `.\mvnw.cmd`.

### 1. Configure the application

Create a `.env` file in the project root:

```dotenv
DB_USERNAME=inventory_user
DB_PASSWORD=replace-with-a-database-password
JWT_SECRET=replace-with-a-random-secret-at-least-32-bytes-long
ADMIN_USERNAME=admin
ADMIN_PASSWORD=replace-with-an-admin-password
```

Replace the example values before starting. The JWT secret must contain at least 32 bytes of text. The `.env` file is ignored by Git, and Docker Compose passes these values to the containers.

On startup, the application creates an admin account using these credentials if that username does not already exist. Changing `ADMIN_PASSWORD` later does not reset an existing account's password.

### 2. Build the application

```powershell
.\mvnw.cmd clean package -DskipTests
```

The Dockerfile copies the built JAR from `target/`, so build it before starting the app container. Tests are skipped for this packaging step because the application context test requires database access and configuration.

### 3. Start PostgreSQL, then the application

```powershell
docker compose up -d postgres
docker compose logs -f postgres
```

Wait for PostgreSQL to report that it is ready to accept connections, then press Ctrl+C to leave the log viewer and start the app:

```powershell
docker compose up -d --build app
docker compose logs -f app
```

The API runs at `http://localhost:8080`. Flyway applies the database migrations during startup. Ports `5432` and `8080` must be available.

The current Compose setup has no explicit database volume or readiness check. Keep the PostgreSQL container if you want to retain demo data, and wait for the database before starting the app.

### Running without the app container

You can also run Spring Boot locally against PostgreSQL. Create a database named `InventoryTracker` on `localhost:5432` and add a `secrets.properties` file in the project root with the same five keys shown above, then run:

```powershell
.\mvnw.cmd spring-boot:run
```

The application imports `secrets.properties` automatically. A `.env` file alone is used by Compose and is not automatically loaded when running Spring Boot locally.

To run the existing service unit tests:

```powershell
.\mvnw.cmd test '-Dtest=StockLevelServiceTest,InventoryTransactionServiceTest'
```

## Usage / Demo

Open [Swagger UI](http://localhost:8080/swagger-ui/index.html) after the application starts. This is currently an API project; Swagger UI or an API client is the way to try it.

### 1. Sign in

Send `POST /auth/login` with the admin credentials from your configuration:

```json
{
  "username": "admin",
  "password": "your-configured-admin-password"
}
```

The response body is the JWT itself. For protected requests, send it in the header:

```http
Authorization: Bearer <your-token>
```

Tokens expire after one hour. In Swagger, use **Authorize** where available and paste the token. If an operation does not show an authorization control, use an API client and set the header directly.

### 2. Create the demo records

As an admin, create a product, location, and supplier using these requests. Keep the IDs returned by the API for the next steps.

`POST /products`

```json
{
  "productName": "Coffee Beans",
  "unit": "bag",
  "sku": "COFFEE-001",
  "reorderLevel": 10,
  "unitCost": 12.50
}
```

`POST /locations`

```json
{
  "locationName": "Main Store"
}
```

`POST /suppliers`

```json
{
  "supplierName": "Local Coffee Roasters",
  "email": "orders@example.com",
  "phoneNumber": "555-0100"
}
```

### 3. Receive stock

Send `POST /inventory-transactions`, replacing the example IDs with the ones returned above:

```json
{
  "productId": 1,
  "locationId": 1,
  "supplierId": 1,
  "quantity": 50,
  "transactionType": "STOCK_IN",
  "note": "Opening delivery"
}
```

A supplier is required for `STOCK_IN`. The first incoming movement creates the stock record for that product and location.

### 4. Record a sale or usage

Send another `POST /inventory-transactions`:

```json
{
  "productId": 1,
  "locationId": 1,
  "quantity": 5,
  "transactionType": "STOCK_OUT",
  "note": "Used during morning service"
}
```

The location now has 45 bags. To correct a physical count, use `STOCK_ADJUSTMENT`: its quantity is the new total on hand. All movement quantities must currently be greater than zero, including adjustments, and an adjustment requires an existing stock record.

### 5. Check stock and history

| Request | What it shows |
| --- | --- |
| `GET /stock-levels` | All current stock balances |
| `GET /stock-levels?locationId=1` | Stock at one location |
| `GET /products/1/stock` | Stock for one product across locations |
| `GET /inventory-transactions?productId=1&locationId=1` | Movement history for a product at a location |
| `GET /inventory-transactions?from=2026-01-01T00:00:00&to=2026-12-31T23:59:59` | Movement history within a date range |

Try recording a `STOCK_OUT` larger than the available balance to see the insufficient-stock response. Stock changes go through the transaction endpoint, keeping the movement history alongside the current balance.
