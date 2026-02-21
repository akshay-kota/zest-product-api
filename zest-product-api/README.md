# Zest Product API

A Spring Boot REST API for managing products with JWT authentication, role-based authorization, and Docker support.

---

## 🚀 Features

- CRUD operations for Products
- JWT Authentication
- Refresh Token mechanism
- Role-based Authorization (ADMIN / USER)
- Global Exception Handling
- Swagger API Documentation
- Pagination & Sorting
- Unit & Controller Tests
- Dockerized (App + MySQL)
- Profile-based configuration (Local & Docker)

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.5.11
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Docker & Docker Compose
- Swagger (Springdoc OpenAPI)
- JUnit & Mockito

---

## ⚙️ How To Run Locally

1. **Make sure MySQL is running on your system.**
2. **Create database:**

```sql
CREATE DATABASE zestdb;
```
3. **Run Application :**
```sql
 mvn spring-boot:run
```
4. **Access Swagger:**

```sql
 http://localhost:8585/swagger-ui/index.html
``` 

5. **How To Run With Docker**

```sql
 docker compose up --build
```
 Access Swagger:
```sql
 http://localhost:8585/swagger-ui/index.html
 ```
 To stop containers:
```sql
 docker compose down
 ```

6. **Default Credentials**

| Role  | Username | Password |
| ----- | -------- | -------- |
| ADMIN | admin    | admin123 |
| USER  | user     | user123  |

7. **Authentication Flow**

  Call POST /api/v1/auth/login

  Copy the access token

  Click Authorize in Swagger

  Enter:

  Bearer your_access_token

  Now access secured APIs
  
8. **API Endpoints**

POST /api/v1/auth/login

POST /api/v1/auth/refresh

Product APIs

POST /api/v1/products (ADMIN only)

GET /api/v1/products/{id} (USER / ADMIN)

GET /api/v1/products

PUT /api/v1/products/{id} (ADMIN only)

DELETE /api/v1/products/{id} (ADMIN only)

9. **Running Tests**

```sql
 mvn test
 ```
10. **Project Structure**

controller/
service/
repository/
entity/
dto/
security/
config/
exception/

11. **Author**

Akshay Kota \
Java Developer
