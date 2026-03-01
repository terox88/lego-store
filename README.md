#  Lego Store Manager API

Recruitment project -- Spring Boot REST API for managing Lego sets,
stock levels, pricing and availability.

The application provides filtering, sorting, JWT authentication,
optimistic locking and Dockerized environment for easy setup.

------------------------------------------------------------------------
## Project Structure

Backend repository (this repo):  
 https://github.com/terox88/lego-store

Frontend repository (React):  
 https://github.com/terox88/lego-store-frontend

The frontend is a separate React application communicating with this API.

------------------------------------------------------------------------

##  Features

-   CRUD operations for Lego sets
-   Advanced filtering (series, condition, availability, price range,
    discontinued)
-   Sorting (dynamic `field,direction`)
-   Stock management (warehouse & store)
-   JWT Authentication (stateless)
-   Optimistic Locking (`@Version`)
-   Global Exception Handling
-   Request counter (basic metrics endpoint)
-   Swagger / OpenAPI documentation
-   Dockerized setup (PostgreSQL + Backend)
-   Unit and integration tests

------------------------------------------------------------------------

##  Architecture

The project follows layered architecture:

controller → service → repository → database

### Main packages:

-   `controller` -- REST endpoints
-   `service` -- business logic
-   `repository` -- Spring Data JPA repositories
-   `domain` -- entities and enums
-   `dto` -- request & response models
-   `specification` -- dynamic filtering (JPA Specification)
-   `security` -- JWT authentication
-   `exception` -- global exception handling
-   `configuration` -- Swagger, Security, Filters

------------------------------------------------------------------------

##  Technologies

-   Java 21
-   Spring Boot 3.5.11
-   Spring Security (JWT)
-   Spring Data JPA
-   PostgreSQL
-   Hibernate
-   Swagger / springdoc-openapi
-   Docker & Docker Compose
-   JUnit 5 / MockMvc

------------------------------------------------------------------------

##  Authentication (JWT)

Authentication is stateless and based on JWT tokens.

### Login

POST /auth/login

Request body:

{ "username": "legoludek", "password": "legoludek123" }

Response:

{ "token": "JWT_TOKEN" }

Add header:

Authorization: Bearer `<token>`{=html}

------------------------------------------------------------------------

##  Filtering & Sorting

Endpoint:

GET /api/lego-sets

Supports:

-   `series`
-   `condition`
-   `minPrice`
-   `maxPrice`
-   `availabilityType`
-   `discontinued`
-   `sort` (format: `field,direction`)

Example:

GET /api/lego-sets?series=MARVEL&sort=basePrice,asc

------------------------------------------------------------------------

##  Optimistic Locking

Entity `LegoSet` uses:

@Version private Long version;

If concurrent modification occurs, the API returns HTTP 409 CONFLICT.

------------------------------------------------------------------------

##  Metrics

GET /metrics/requests

Returns total number of handled HTTP requests.

------------------------------------------------------------------------

## Running Full Application with Docker

Required folder structure:

C:\java
├── lego-store
└── lego-store-frontend

The docker-compose file builds frontend using:

context: ../lego-store-frontend

If structure differs, adjust the path.

Run:

docker compose up --build

Access:
Frontend: http://localhost:3000  
Backend: http://localhost:8080  
Swagger: http://localhost:8080/swagger-ui.html

PostgreSQL runs inside Docker network (not publicly exposed).

------------------------------------------------------------------------

##  Running Tests

./gradlew test

Includes controller, repository, domain and integration tests.

------------------------------------------------------------------------

##  Author Michał Morawski

Recruitment project created as part of backend portfolio development.
