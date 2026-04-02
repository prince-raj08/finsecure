# 🏦 FinSecure - Personal Finance Management System

FinSecure is a robust **Java Full Stack Backend Application** that helps users manage and analyze their financial transactions securely.
It is built using **Spring Boot 3.x** and follows **industry-standard practices** like layered architecture, secure authentication, and role-based access control.

---

## 🚀 Features

* 🔐 **Secure Authentication**

  * Password hashing using BCrypt
  * Secure login & registration

* 👥 **Role-Based Access Control (RBAC)**

  * **ADMIN** → Full access (CRUD on users & records)
  * **VIEWER** → Read-only access

* 📊 **Dynamic Dashboard**

  * Total Income
  * Total Expense
  * Net Balance

* ⚠️ **Global Exception Handling**

  * Consistent API responses with custom error messages

* ✅ **Input Validation**

  * Using Hibernate Validator

* 📜 **Professional Logging**

  * Implemented using SLF4J / Logback

---

## 🛠️ Tech Stack

| Layer      | Technology                  |
| ---------- | --------------------------- |
| Backend    | Java 17, Spring Boot 3.x    |
| Security   | Spring Security 6           |
| Database   | MySQL 8                     |
| ORM        | Spring Data JPA (Hibernate) |
| Validation | Jakarta Validation API      |
| Logging    | SLF4J / Logback             |
| Testing    | Postman                     |

---

## 🏗️ Project Architecture

The project follows a **Layered Architecture** for better scalability and maintainability:

```
com.prince.finance.finsecure
├── config          # Security configuration
├── controller      # REST Controllers
├── dto             # Data Transfer Objects
├── entities        # JPA Entities
├── enums           # Enum classes
├── exceptions      # Global Exception Handling
├── repository      # Database access layer
└── services        # Business logic
```

---

## 📊 Database Schema

### 🧑 User Table

* id
* username
* email
* password (hashed)
* role
* status

### 💰 Financial Records Table

* id
* amount
* category
* type (INCOME / EXPENSE)
* user_id (Foreign Key)

👉 Relationship: **One-to-Many (One User → Many Records)**

---

## 🔐 Security Implementation

* Passwords are stored using **BCrypt hashing**
* Role-based authorization using **Spring Security**
* Method-level security using `@EnableMethodSecurity`
* CSRF configured for REST APIs

---

## ⚙️ Setup Instructions

### 1️⃣ Prerequisites

* Java 17+
* Maven
* MySQL Server

---

### 2️⃣ Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finsecure_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 3️⃣ Run Application

```bash
mvn clean install
mvn spring-boot:run
```

---

## 📡 API Endpoints

### 🔹 Register User

```
POST /api/user/register
```

**Request Body**

```json
{
  "username": "prince_raj",
  "email": "prince@dev.com",
  "password": "SecurePassword123"
}
```

---

### 🔹 Get Dashboard Summary

```
GET /api/records/dashboard/{userId}
```

**Response**

```json
{
  "totalIncome": 50000.0,
  "totalExpense": 12500.0,
  "netBalance": 37500.0
}
```

---

## 🔮 Future Enhancements

* 🔑 JWT Authentication (Token-based security)
* 📊 Data Visualization (Charts)
* 📄 Export Reports (PDF/Excel)
* 📧 Email Notifications
* 🌐 Frontend Integration (React)

---

## 👨‍💻 Author

**Prince Raj**
Java Full Stack Developer

* 🔗 LinkedIn: https://www.linkedin.com/in/prince-raj-10a2a42a8
* 💻 GitHub: https://github.com/prince-raj08

---

## ⭐ Support

If you like this project, please ⭐ the repository and share it!

---
