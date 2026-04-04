# 🔐 FinSecure — Finance Dashboard Backend

A role-based finance management backend built with **Spring Boot**, **JWT Authentication**, and **MySQL**. This system allows admins to manage users and financial records, while analysts and viewers can access data based on their permissions.

---

## 🛠️ Tech Stack

| Technology | Version |
|---|---|
| Java | 25 |
| Spring Boot | 3.5.x |
| Spring Security | 6.5.x |
| JWT (jjwt) | 0.12.6 |
| MySQL | 8.0 |
| Hibernate / JPA | 6.6.x |
| Lombok | 1.18.x |
| Maven | 3.x |

---

## 📁 Project Structure

```
finsecure/
├── controller/
│   ├── AuthController.java
│   ├── UserController.java
│   ├── FinancialRecordController.java
│   └── DashboardController.java
├── services/
│   ├── UserServices.java / UserServicesImpl.java
│   ├── FinancialServices.java / FinaicialServicesImpl.java
│   └── DashboardService.java / DashboardServiceImpl.java
├── repository/
│   ├── UserRepository.java
│   └── FinancialRecordRepository.java
├── entities/
│   ├── User.java
│   └── Financial_record.java
├── DTO/
│   ├── UserDto.java / UserResponseDto.java
│   ├── LoginDto.java
│   ├── FinancialRecordDto.java / FinancialRecordResponseDto.java
│   ├── UserFinancialRecordDto.java
│   ├── DashboardSummaryDto.java
│   ├── CategorySummaryDto.java
│   └── MonthlyTrendDto.java
├── security/
│   ├── SecurityConfig.java
│   ├── JwtService.java
│   ├── JwtFilter.java
│   └── CustomUserDetailsService.java
├── enums/
│   ├── Role.java (ADMIN, ANALYST, VIEWER)
│   ├── Status.java (ACTIVE, INACTIVE)
│   ├── Type.java (INCOME, EXPENSE)
│   └── Category.java
└── exceptions/
    ├── CommonExceptions.java
    └── GlobalExceptionHandler.java
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.x

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/your-username/finsecure.git
cd finsecure
```

**2. Create MySQL database**
```sql
CREATE DATABASE finance_db;
```

**3. Configure `application.properties`**
```properties
spring.application.name=finsecure

spring.datasource.url=jdbc:mysql://localhost:3306/finance_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

jwt.secret=your-256-bit-secret-key
jwt.expiration=86400000
spring.jpa.open-in-view=false
```

**4. Generate JWT Secret Key**
```java
public class GenerateSecret {
    public static void main(String[] args) {
        byte[] key = new byte[32];
        new java.security.SecureRandom().nextBytes(key);
        System.out.println(java.util.Base64.getEncoder().encodeToString(key));
    }
}
```

**5. Run the application**
```bash
mvn spring-boot:run
```

App will start on: `http://localhost:8080`

---

## 🔑 Authentication

JWT Token based authentication. Token must be sent in every request header:

```
Authorization: Bearer <your_token>
```

Token expires in **24 hours** (86400000 ms).

---

## 👥 Roles & Permissions

| Action | VIEWER | ANALYST | ADMIN |
|---|---|---|---|
| Register / Login | ✅ | ✅ | ✅ |
| View own profile | ✅ | ✅ | ✅ |
| View all users | ❌ | ❌ | ✅ |
| Update user status | ❌ | ❌ | ✅ |
| Create financial record | ❌ | ❌ | ✅ |
| View financial records | ✅ | ✅ | ✅ |
| Update financial record | ❌ | ❌ | ✅ |
| Delete financial record | ❌ | ❌ | ✅ |
| View dashboard summary | ❌ | ✅ | ✅ |
| View category summary | ❌ | ✅ | ✅ |
| View monthly trend | ❌ | ✅ | ✅ |
| View recent transactions | ✅ | ✅ | ✅ |

---

## 📡 API Endpoints

### 🔐 Auth
| Method | URL | Access | Description |
|---|---|---|---|
| POST | `/api/auth/login` | Public | Login and get JWT token |

**Login Request:**
```json
{
    "email": "admin@gmail.com",
    "password": "Admin@123"
}
```

**Login Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "email": "admin@gmail.com",
    "role": "ROLE_ADMIN"
}
```

---

### 👤 User Management
| Method | URL | Access | Description |
|---|---|---|---|
| POST | `/api/user/register` | Public | Register new user |
| GET | `/api/user/getProfile` | ALL | Get logged-in user profile |
| GET | `/api/user/all` | ADMIN | Get all users |
| PUT | `/api/user/{id}/status` | ADMIN | Update user status |

**Register Request:**
```json
{
    "username": "prince",
    "email": "prince@gmail.com",
    "password": "Prince@123",
    "role": "VIEWER"
}
```

**Update Status:**
```
PUT /api/user/1/status?status=INACTIVE
```

---

### 💰 Financial Records
| Method | URL | Access | Description |
|---|---|---|---|
| POST | `/api/financial_record/create` | ADMIN | Create record |
| GET | `/api/financial_record/all` | ADMIN, ANALYST | All records grouped by user |
| GET | `/api/financial_record/{id}` | ADMIN | Get record by ID |
| POST | `/api/financial_record/{id}/update` | ADMIN | Update record |
| DELETE | `/api/financial_record/{id}/delete` | ADMIN | Soft delete record |
| GET | `/api/financial_record/user/{userId}` | ADMIN, ANALYST | User's records |
| GET | `/api/financial_record/filter/type` | ALL | Filter by type |
| GET | `/api/financial_record/filter/category` | ALL | Filter by category |
| GET | `/api/financial_record/filter/date` | ALL | Filter by date range |

**Create Record Request:**
```json
{
    "userId": 1,
    "amount": 50000,
    "type": "INCOME",
    "category": "SALARY",
    "date": "2026-04-01T10:00:00",
    "note": "April salary"
}
```

**Filter by Type:**
```
GET /api/financial_record/filter/type?type=INCOME
```

**Filter by Date:**
```
GET /api/financial_record/filter/date?start=2026-04-01T00:00:00&end=2026-04-30T23:59:59
```

---

### 📊 Dashboard
| Method | URL | Access | Description |
|---|---|---|---|
| GET | `/api/dashboard/{userId}/summary` | ADMIN, ANALYST | Total income, expense, balance |
| GET | `/api/dashboard/{userId}/category` | ADMIN, ANALYST | Category wise totals |
| GET | `/api/dashboard/{userId}/monthly` | ADMIN, ANALYST | Monthly trends |
| GET | `/api/dashboard/{userId}/recent` | ALL | Recent 5 transactions |

**Summary Response:**
```json
{
    "totalIncome": 60000.00,
    "totalExpense": 31000.00,
    "netBalance": 29000.00
}
```

**Category Summary Response:**
```json
[
    { "category": "SALARY", "totalAmount": 50000.00 },
    { "category": "RENT", "totalAmount": 15000.00 },
    { "category": "FOOD", "totalAmount": 8000.00 }
]
```

**Monthly Trend Response:**
```json
[
    {
        "year": 2026,
        "month": 4,
        "totalIncome": 60000.00,
        "totalExpense": 31000.00,
        "netBalance": 29000.00
    }
]
```

---

## 🗄️ Database Schema

### User Table
| Column | Type | Description |
|---|---|---|
| id | BIGINT | Primary Key |
| username | VARCHAR | User's name |
| email | VARCHAR | Unique email |
| password | VARCHAR | BCrypt hashed |
| role | ENUM | ADMIN, ANALYST, VIEWER |
| status | ENUM | ACTIVE, INACTIVE |

### Financial Record Table
| Column | Type | Description |
|---|---|---|
| fid | BIGINT | Primary Key |
| amount | DECIMAL | Transaction amount |
| type | ENUM | INCOME, EXPENSE |
| category | ENUM | SALARY, RENT, FOOD, etc. |
| date | DATETIME | Transaction date & time |
| note | VARCHAR | Optional description |
| user_id | BIGINT | Foreign Key → User |
| is_deleted | BOOLEAN | Soft delete flag |

---

## 🛡️ Security Features

- **JWT Authentication** — Stateless token-based auth
- **BCrypt Password Hashing** — Passwords never stored as plain text
- **Role Based Access Control** — `@PreAuthorize` on every endpoint
- **Account Locking** — INACTIVE users cannot login
- **Input Validation** — `@Valid` on all request bodies
- **Global Exception Handler** — Consistent error responses

---

## ✅ Assumptions Made

- Admin is created manually in DB or via register endpoint with `role: ADMIN`
- Only Admin can create/update/delete financial records
- Soft delete is used — records are never permanently deleted
- JWT token expires in 24 hours
- Password must contain uppercase, lowercase, number and special character

---

## 🚀 Sample Flow

```
1. Register user    → POST /api/user/register
2. Login            → POST /api/auth/login → Get token
3. Create record    → POST /api/financial_record/create (ADMIN)
4. View summary     → GET /api/dashboard/{userId}/summary (ANALYST/ADMIN)
5. View recent      → GET /api/dashboard/{userId}/recent (ALL)
```

---

## 👨‍💻 Developer

**Prince Raj** — Java Full Stack Developer

- 📧 Email: prince.raj.080870@gmail.com
- 📱 Phone: 7632827390
- 💼 LinkedIn: [linkedin.com/in/prince-raj-10a2a42a8](https://www.linkedin.com/in/prince-raj-10a2a42a8)
- 🐙 GitHub: [github.com/prince-raj08](https://github.com/prince-raj08)
- 📦 Repository: [github.com/prince-raj08/finsecure](https://github.com/prince-raj08/finsecure)

---
