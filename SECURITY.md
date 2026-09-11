# Security Rules

| Method |  | Endpoint |            | Access |
--------------------------------------------------
| POST   |  |/auth/login|           |Public|
| POST   |  |/register  |           |Public|
| GET    |  |/users/{id}|           |ADMIN|
| GET    |  |/users     |           |ADMIN|
| PUT    |  |/users/{id}|           |ADMIN|
| PUT    |  |/users/{id}/password|  |ADMIN|
| PUT    |  |/users{id}/role|       |ADMIN|
| DELETE |  |/users/{id}|            |ADMIN|

| GET    |  |/inventory-transactions/{id}| |ADMIN|
| GET    |  |/inventory-transactions     | |ADMIN|
| POST   |  |/inventory-transactions     | |ADMIN or STAFF|
| PUT    |  |/inventory-transactions/{id}| |ADMIN|
| DELETE |  |/inventory-transactions/{id}| |ADMIN|

| GET    |  |/locations/{id}| |ADMIN or STAFF|
| GET    |  |/locations     | |ADMIN or STAFF|
| POST   |  |/locations     | |ADMIN|
| PUT    |  |/locations/{id}| |ADMIN|
| DELETE |  |/locations/{id}| |ADMIN|

| GET    |  |/products/{id}|  |ADMIN or STAFF|
| GET    |  |/products     |  |ADMIN or STAFF|
| POST   |  |/products     |  |ADMIN|
| PUT    |  |/products/{id}|  |ADMIN| 
| DELETE |  |/products/{id}|  |ADMIN|

| GET    |  |/stock-levels/{productId}/{locationId}|  |ADMIN or STAFF|
| GET    |  |/stock-levels                         |  |ADMIN or STAFF|
| POST   |  |/stock-levels                         |  |ADMIN|
| PUT    |  |/stock-levels/{productId}/{locationId}|  |ADMIN| 
| DELETE |  |/stock-levels/{productId}/{locationId}|  |ADMIN|

| GET    |  |/suppliers/{id}|  |ADMIN or STAFF|
| GET    |  |/suppliers     |  |ADMIN or STAFF|
| POST   |  |/suppliers     |  |ADMIN|
| PUT    |  |/suppliers/{id}|  |ADMIN| 
| DELETE |  |/suppliers/{id}|  |ADMIN|
