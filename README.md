# 🎓 Tuition Management System       
  
A backend-based **Tuition Management System** developed using **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL**.

The project is divided into three connected modules:

- 👨‍🎓 **Student Management**  
- 👨‍🏫 **Teacher & Class Management**
- 💰 **Fees & Performance Management**

---

## 📌 Project Structure

```text
Tuition-management-system/
│
├── student-service/
│   └── Student Management Module
│
├── teacher-service/
│   └── Teacher & Class Management Module
│
├── fees-service/
│   └── Fees & Performance Module
│
└── README.md
```

---

## 🚀 Features

### 👨‍🎓 Student Management

- Add student
- Update student
- Delete student
- Search student
- View student details
- Enroll student
- Assign student to batch

### 👨‍🏫 Teacher & Class Management

- Add teacher
- Update teacher
- View teachers
- Create batches
- View batches
- Assign teacher to batch
- View teacher-batch assignments
- Create subjects
- View subjects
- Mark attendance

### 💰 Fees & Performance Management

- Create fee structure
- Record fee payment
- Check pending fees
- Generate fee receipt
- Enter examination marks
- Calculate percentage
- Calculate grade
- Generate student performance report

---

# 🔗 System Workflow

```text
                    ┌───────────────┐
                    │    Student    │
                    └───────┬───────┘
                            │
                            ▼
                    ┌───────────────┐
                    │   Enrollment  │
                    └───────┬───────┘
                            │
                            ▼
                    ┌───────────────┐
                    │     Batch     │
                    └───────┬───────┘
                            │
                 ┌──────────┴──────────┐
                 ▼                     ▼
        ┌────────────────┐     ┌────────────────┐
        │ Teacher-Batch  │     │   Attendance   │
        └───────┬────────┘     └───────┬────────┘
                │                      │
                ▼                      ▼
           ┌──────────┐          ┌──────────┐
           │ Teacher  │          │ Student  │
           └────┬─────┘          └──────────┘
                │
                ▼
           ┌──────────┐
           │ Subject  │
           └──────────┘

                    Student
                       │
              ┌────────┴────────┐
              ▼                 ▼
         ┌──────────┐     ┌─────────────┐
         │   Fees   │     │ Performance │
         └──────────┘     └─────────────┘
```

---

# 🧩 Module 1 — Student Management

The Student Management module handles student records and enrollment.

## Features

- Add student
- Update student
- Delete student
- Search student
- View student details
- Student enrollment
- Assign student to batch

## Main Entities

- `Student`
- `Enrollment`
- `Batch`

## Enrollment Flow

```text
Student
   │
   ▼
Select Batch
   │
   ▼
Create Enrollment
   │
   ▼
Enrollment Status = ACTIVE
```

The enrollment connects a student with a batch.

Example:

```text
Student ID : 1
Batch ID   : 1
Status     : ACTIVE
```

The enrollment information is also used by the Attendance module to verify whether the student is actively enrolled in the selected batch.

---

# 🧩 Module 2 — Teacher & Class Management

The Teacher & Class Management module handles teachers, batches, subjects, teacher-batch assignments, and attendance.

## 👨‍🏫 Teacher Management

### Teacher Details

- Teacher ID
- Name
- Email
- Phone
- Specialization

### APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/teachers` | Add teacher |
| `GET` | `/api/teachers` | Get teachers |
| `PUT` | `/api/teachers/{id}` | Update teacher |

### Pagination

```text
GET /api/teachers?page=0&size=10
```

---

## 🕘 Batch Management

The institute uses fixed batches with predefined timings.

| Batch | Start Time | End Time |
|---|---:|---:|
| `MRG_BATCH` | 09:00 AM | 11:00 AM |
| `AFN_BATCH` | 01:00 PM | 03:00 PM |
| `EVNG_BATCH` | 05:00 PM | 07:00 PM |

### Batch Details

- Batch ID
- Batch name
- Start time
- End time

### APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/batches` | Create batch |
| `GET` | `/api/batches` | Get batches |

### Pagination

```text
GET /api/batches?page=0&size=10
```

> Batch timings are stored directly in the Batch entity because the institute uses fixed batch time slots. A separate schedule entity is not required for these fixed timings.

---

## 🔗 Teacher-Batch Assignment

A teacher can be assigned to a batch through the `TeacherBatch` entity.

### Structure

```text
TeacherBatch
├── id
├── teacherId
└── batchId
```

### APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/teacher-batches` | Assign teacher to batch |
| `GET` | `/api/teacher-batches` | View assignments |

### Example

```text
POST /api/teacher-batches?teacherId=1&batchId=1
```

This creates a relationship between:

```text
Teacher ID 1
      │
      ▼
Batch ID 1
```

Duplicate teacher-batch assignments are prevented.

---

## 📚 Subject Management

The system supports subject creation and subject listing.

### Example Subjects

- Java
- Python
- MySQL

### Subject Details

- Subject ID
- Name
- Description

### APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/subjects` | Create subject |
| `GET` | `/api/subjects` | Get subjects |

### Pagination

```text
GET /api/subjects?page=0&size=10
```

---

## 📝 Attendance Management

Attendance is recorded using:

- Teacher ID
- Batch ID
- Student ID
- Date
- Present/Absent status

### Attendance Structure

```text
Attendance
├── id
├── teacherId
├── batchId
├── studentId
├── date
└── present
```

### API

```text
POST /api/attendance
```

### Example Request

```json
{
  "teacherId": 1,
  "batchId": 1,
  "studentId": 1,
  "date": "2026-09-16",
  "present": true
}
```

### Attendance Validation

Before saving attendance:

1. Check whether the student exists in the enrollment.
2. Check whether the enrollment belongs to the selected batch.
3. Check whether the enrollment status is `ACTIVE`.
4. Check whether attendance has already been marked for that student, batch, and date.
5. Save attendance if all validations pass.

### Attendance Flow

```text
Attendance Request
        │
        ▼
Check Student Enrollment
        │
        ▼
Is Enrollment ACTIVE?
    ┌───┴───┐
   YES      NO
    │        │
    ▼        ▼
Check      Reject
Duplicate
    │
    ▼
Save Attendance
```

---

# 🧩 Module 3 — Fees & Performance Management

The Fees & Performance module handles financial and academic information.

## 💰 Fee Management

### Features

- Create fee structure
- Record fee payment
- Check pending fees
- Generate fee receipt

### Flow

```text
Student
   │
   ▼
Fee Structure
   │
   ▼
Fee Payment
   │
   ▼
Pending Amount
   │
   ▼
Fee Receipt
```

---

## 📊 Performance Management

### Features

- Enter examination marks
- Calculate percentage
- Calculate grade
- Generate performance report

### Flow

```text
Student
   │
   ▼
Examination Marks
   │
   ▼
Percentage
   │
   ▼
Grade
   │
   ▼
Performance Report
```

---

# 🗄️ Database

The application uses **MySQL** as the database.

## Main Tables

```text
students
enrollments

teacher
batch
teacher_batch
subject
attendance

fee_structures
fee_payments
performances
```

The modules share common database information where required.

---

# 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot | Backend framework |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| MySQL | Database |
| Maven | Build and dependency management |
| Lombok | Reducing boilerplate code |
| Swagger UI | API documentation and testing |
| Git | Version control |
| GitHub | Repository |

---

# 🏗️ Backend Architecture

The application follows a layered architecture.

```text
┌────────────────────┐
│     Controller     │
│   REST API Layer   │
└─────────┬──────────┘
          │
          ▼
┌────────────────────┐
│      Service       │
│   Business Logic   │
└─────────┬──────────┘
          │
          ▼
┌────────────────────┐
│     Repository     │
│    Data Access     │
└─────────┬──────────┘
          │
          ▼
┌────────────────────┐
│       MySQL        │
│      Database      │
└────────────────────┘
```

## Controller Layer

Responsible for:

- Receiving HTTP requests
- Reading request parameters
- Reading request bodies
- Calling service methods
- Returning API responses

## Service Layer

Responsible for:

- Business logic
- Validation
- Duplicate checks
- Enrollment verification
- Attendance verification
- Calling repositories

## Repository Layer

Responsible for:

- Database operations
- CRUD operations
- Derived query methods

Spring Data JPA provides standard CRUD operations through `JpaRepository`.

---

# 📦 API Response Structure

The application uses a common response structure for implemented APIs.

## Successful Response

```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Ravi Kumar"
  },
  "error": null,
  "meta": null
}
```

## Paginated Response

```json
{
  "success": true,
  "data": [],
  "error": null,
  "meta": {
    "page": 0,
    "size": 10,
    "totalElements": 20,
    "totalPages": 2
  }
}
```

---

# 📄 Pagination

Pagination is implemented for listing APIs where required.

### Example

```text
GET /api/teachers?page=0&size=10
```

| Parameter | Description | Default |
|---|---|---:|
| `page` | Page number | `0` |
| `size` | Number of records per page | `10` |

Spring Data classes used:

- `Pageable`
- `PageRequest`
- `Page`

---

# ⚠️ Exception Handling

The application uses custom exceptions and centralized exception handling.

Examples include:

- Resource not found
- Duplicate resource
- Bad request
- Validation errors
- Student not enrolled
- Duplicate attendance
- Duplicate teacher-batch assignment

Global exception handling is implemented using:

```java
@RestControllerAdvice
```

### Example Error Response

```json
{
  "success": false,
  "data": null,
  "error": {
    "code": "RESOURCE_NOT_FOUND",
    "message": "Resource not found"
  }
}
```

---

# 🔐 Business Rules

## Teacher

- Email should not be duplicated.
- Phone number should not be duplicated.
- Teacher must exist before updating.

## Teacher-Batch Assignment

- Duplicate teacher-batch assignments are prevented.

## Enrollment

- Duplicate student-batch enrollment is prevented.
- Enrollment status is maintained.

## Attendance

- Student must be actively enrolled in the selected batch.
- Duplicate attendance for the same student, batch, and date is prevented.

---

# 🔢 ID Strategy

The main entities use `Long` IDs.

```text
Teacher ID      → Long
Batch ID        → Long
Subject ID      → Long
Student ID      → Long
Attendance ID   → Long
```

IDs are generated using database identity generation where configured.

Example:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

---

# 🕘 Batch Configuration

```text
MRG_BATCH
09:00 AM - 11:00 AM

AFN_BATCH
01:00 PM - 03:00 PM

EVNG_BATCH
05:00 PM - 07:00 PM
```

The batch stores its own start and end time because these are fixed institute batch timings.

---

# 🚀 Getting Started

## Prerequisites

Install the following:

- Java 17
- MySQL
- Maven
- Git
- IntelliJ IDEA

---

## Clone Repository

```bash
git clone https://github.com/Nikithayyadav/Tuition-management-system.git
```

```bash
cd Tuition-management-system
```

---

# 🗄️ Database Configuration

Create the MySQL database used by the project.

Example:

```sql
CREATE DATABASE tuition_db;
```

Configure the datasource in the Spring Boot configuration.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tuition_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

Replace `YOUR_PASSWORD` with your MySQL password.

> Use the actual database name and configuration defined in the current project configuration if they differ from the example.

---

# ▶️ Run the Application

Make sure MySQL is running.

Open the project in IntelliJ IDEA and run the Spring Boot application.

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

---

# 📖 Swagger UI

After starting the application, Swagger UI can be used to test the REST APIs.

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger allows you to:

- View available APIs
- Enter request data
- Execute APIs
- View responses
- Test successful scenarios
- Test error scenarios

---

# 🧪 Complete Testing Flow

The following sequence can be used to demonstrate the integrated system.

## 1. Create Teacher

```text
POST /api/teachers
```

Example request:

```json
{
  "name": "Ravi Kumar",
  "phone": "9876543210",
  "email": "ravi@example.com",
  "specialization": "Java"
}
```

---

## 2. Create Batch

```text
POST /api/batches
```

Example request:

```json
{
  "name": "MRG_BATCH",
  "startTime": "09:00",
  "endTime": "11:00"
}
```

---

## 3. Create Subject

```text
POST /api/subjects
```

Example request:

```json
{
  "name": "Java",
  "description": "Core Java programming"
}
```

---

## 4. Create Student

Use the Student Management APIs to create a student.

---

## 5. Enroll Student

```text
POST /students/{studentId}/enrollment
```

Example request:

```json
{
  "batchId": 1
}
```

---

## 6. Assign Teacher to Batch

```text
POST /api/teacher-batches?teacherId=1&batchId=1
```

---

## 7. Mark Attendance

```text
POST /api/attendance
```

Example request:

```json
{
  "teacherId": 1,
  "batchId": 1,
  "studentId": 1,
  "date": "2026-09-16",
  "present": true
}
```

---

## 8. Manage Fees

```text
Create Fee Structure
        ↓
Record Fee Payment
        ↓
Check Pending Fees
        ↓
Generate Fee Receipt
```

---

## 9. Manage Performance

```text
Enter Examination Marks
        ↓
Calculate Percentage
        ↓
Calculate Grade
        ↓
Generate Performance Report
```

---

# 🧪 Test Scenarios

## Teacher

- [x] Add teacher
- [x] Get teachers
- [x] Update teacher
- [x] Duplicate email validation
- [x] Duplicate phone validation
- [x] Non-existing teacher validation
- [x] Pagination

## Batch

- [x] Create batch
- [x] Get batches
- [x] Pagination

## Subject

- [x] Create subject
- [x] Get subjects
- [x] Pagination

## Teacher-Batch

- [x] Assign teacher to batch
- [x] View assignments
- [x] Prevent duplicate assignment

## Student Enrollment

- [x] Enroll student into batch
- [x] Prevent duplicate enrollment
- [x] Maintain enrollment status

## Attendance

- [x] Mark student present
- [x] Mark student absent
- [x] Verify active enrollment
- [x] Prevent duplicate attendance

## Fees

- [x] Create fee structure
- [x] Record payment
- [x] Check pending fees
- [x] Generate fee receipt

## Performance

- [x] Enter examination marks
- [x] Calculate percentage
- [x] Calculate grade
- [x] Generate performance report

---

# ☕ Core Java Concepts

The project demonstrates the following Java concepts.

## Phase 1 — Object-Oriented Programming

- Classes and Objects
- Constructors
- Encapsulation
- Inheritance
- Polymorphism
- Interfaces

## Phase 2 — Collections and Exception Handling

- ArrayList
- HashMap
- HashSet
- Exception Handling
- Custom Exceptions

## Phase 3 — Java Functional Programming

- Lambda Expressions
- Stream API
- `filter()`
- `map()`
- `sorted()`
- `collect()`
- `groupingBy()`

## Phase 4 — Database and Persistence

- JDBC
- MySQL
- CRUD Operations
- JPA
- Hibernate
- Transactions

---

# 🔗 Entity Relationships

```text
Student
   │
   ├──────────────► Enrollment ──────────────► Batch
   │                                             │
   │                                             │
   │                                      TeacherBatch
   │                                             │
   │                                             ▼
   │                                          Teacher
   │
   ├──────────────► Attendance
   │
   ├──────────────► Fee Structure
   │
   ├──────────────► Fee Payment
   │
   └──────────────► Performance
                         │
                         ▼
                    Exam Marks
```

---

# 🎯 Project Objectives

- Digitize tuition institute management
- Reduce manual record keeping
- Centralize student information
- Manage teachers and batches
- Manage student enrollment
- Manage teacher-batch assignments
- Manage subjects
- Track student attendance
- Manage fees and payments
- Track academic performance
- Calculate student grades
- Provide structured REST APIs
- Maintain consistency between modules

---

# 🔮 Future Enhancements

- Authentication and Authorization
- Role-Based Access Control
- Admin Dashboard
- Teacher Dashboard
- Student Dashboard
- Online Fee Payment
- Email and SMS Notifications
- Attendance Reports
- Advanced Performance Analytics
- PDF Fee Receipts
- Automated Student Reports
- Docker Deployment
- Cloud Deployment

---

# 👥 Team Responsibilities

| Member | Module | Responsibilities |
|---|---|---|
| Abhilash | Student Management | Student management, enrollment, batch assignment |
| Nikitha | Teacher & Class Management | Teacher, batch, teacher-batch, subject, attendance |
| Sadhvika | Fees & Performance | Fees, payments, receipts, marks, percentage, grades, reports |

---

# 📊 Project Status

| Module | Status |
|---|---|
| Student Management | ✅ Implemented |
| Student Enrollment | ✅ Implemented |
| Teacher Management | ✅ Implemented |
| Batch Management | ✅ Implemented |
| Teacher-Batch Assignment | ✅ Implemented |
| Subject Management | ✅ Implemented |
| Attendance Management | ✅ Implemented |
| Fee Structure | ✅ Implemented |
| Fee Payment | ✅ Implemented |
| Performance Management | ✅ Implemented |

---

# 👩‍💻 Developer

### Nikitha Yadav

**B.Tech — Computer Science & Engineering (AI)**

Responsibilities in this project:

- Teacher Management
- Batch Management
- Teacher-Batch Assignment
- Subject Management
- Attendance Management

---

# 📂 Repository

[Tuition Management System — GitHub](https://github.com/Nikithayyadav/Tuition-management-system)

---

# 📜 License

This project was developed as an academic project for learning and demonstrating Java, Spring Boot, REST APIs, MySQL, JPA, Hibernate, exception handling, pagination, and backend development concepts.
