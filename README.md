Tuition Management System

A backend-based Tuition Management System developed using Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL.

The project is divided into three connected modules:

Student Management

Teacher & Class Management

Fees & Performance Management

All three modules are maintained in a single repository and use a common database so that student, batch, teacher, attendance, fees, and performance information can work together.

Project Structure

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

Project Overview

The Tuition Management System is designed to manage the major operations of a tuition/coaching institute.

The system manages:

Students

Student enrollment

Teachers

Batches

Teacher-batch assignments

Subjects

Attendance

Fee structures

Fee payments

Pending fees

Fee receipts

Examination marks

Percentage

Grades

Student performance reports

Overall System Flow

Student
   ↓
Enrollment
   ↓
Batch
   ↓
Teacher
   ↓
Subject
   ↓
Attendance
   ↓
Exam / Marks
   ↓
Performance
   ↓
Fees
   ↓
Student Report

Modules

1. Student Management Module

The Student Management module handles student information and student enrollment.

Features

Add student

Update student

Delete student

Search student

View student details

Student enrollment

Assign student to batch

Main Entities

Student

Enrollment

Batch

Enrollment Flow

Student
   ↓
Select Batch
   ↓
Create Enrollment
   ↓
Enrollment Status = ACTIVE

The enrollment connects a student with a batch.

Example:

Student ID: 1
Batch ID: 1
Status: ACTIVE

The enrollment information is also used by the Attendance module to verify whether a student is actively enrolled in a particular batch.

2. Teacher & Class Management Module

The Teacher & Class Management module manages teachers, batches, subjects, teacher-batch assignments, and attendance.

Features

Add teacher

Update teacher

View teachers

Create batches

View batches

Assign teacher to batch

View teacher-batch assignments

Create subjects

View subjects

Mark attendance

Teacher Management

Teacher information includes:

Teacher ID

Name

Email

Phone

Specialization

Teacher APIs

POST /api/teachers
GET  /api/teachers
PUT  /api/teachers/{id}

Pagination

GET /api/teachers?page=0&size=10

Batch Management

The institute uses fixed batches with predefined timings.

Batch

Time

MRG_BATCH

09:00 AM - 11:00 AM

AFN_BATCH

01:00 PM - 03:00 PM

EVNG_BATCH

05:00 PM - 07:00 PM

Each batch contains:

Batch ID

Batch name

Start time

End time

Batch APIs

POST /api/batches
GET  /api/batches

Pagination

GET /api/batches?page=0&size=10

Teacher-Batch Assignment

A teacher can be assigned to a batch using the TeacherBatch entity.

TeacherBatch
├── id
├── teacherId
└── batchId

APIs

POST /api/teacher-batches
GET  /api/teacher-batches

Example:

POST /api/teacher-batches?teacherId=1&batchId=1

Duplicate teacher-batch assignments are prevented.

Subject Management

The system supports subject creation and subject listing.

Example subjects:

Java
Python
MySQL

Subject information includes:

Subject ID

Name

Description

Subject APIs

POST /api/subjects
GET  /api/subjects

Pagination

GET /api/subjects?page=0&size=10

Attendance Management

Attendance is recorded using:

Teacher ID

Batch ID

Student ID

Date

Present/Absent status

Attendance Structure

Attendance
├── id
├── teacherId
├── batchId
├── studentId
├── date
└── present

Attendance API

POST /api/attendance

Example request:

{
  "teacherId": 1,
  "batchId": 1,
  "studentId": 1,
  "date": "2026-09-16",
  "present": true
}

Before marking attendance, the system verifies that the student is actively enrolled in the selected batch.

The system also prevents duplicate attendance for the same student, batch, and date.

Attendance Flow

Attendance Request
        ↓
Check Student Enrollment
        ↓
Is Student ACTIVE?
   ┌────┴────┐
   │         │
  YES        NO
   │         │
   ↓         ↓
Check       Reject
Duplicate
   │
   ↓
Save Attendance

3. Fees & Performance Management Module

The Fees & Performance module manages student fees and academic performance.

Features

Create fee structure

Record fee payment

Check pending fees

Generate fee receipt

Enter examination marks

Calculate percentage

Calculate grade

Generate student performance report

Main Entities

Fee Structure

Fee Payment

Performance

Examination Marks

Module Integration

The three modules are connected through common entities and IDs.

Student and Batch

Student
   ↓
Enrollment
   ↓
Batch

Teacher and Batch

Teacher
   ↓
TeacherBatch
   ↓
Batch

Attendance

Teacher
   ↓
Attendance
   ↑
Student
   ↑
Batch

Performance

Student
   ↓
Examination Marks
   ↓
Percentage
   ↓
Grade
   ↓
Performance Report

Fees

Student
   ↓
Fee Structure
   ↓
Fee Payment
   ↓
Pending Amount / Receipt

Database

The application uses MySQL as the database.

Main Tables

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

Technology Stack

Backend

Java 17

Spring Boot

Spring Data JPA

Hibernate

Maven

Database

MySQL

API Documentation and Testing

Swagger UI

Development Tools

IntelliJ IDEA

MySQL Workbench

Git

GitHub

Libraries

Lombok

Jakarta Persistence API

Backend Architecture

The application follows a layered architecture.

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

Controller Layer

Responsible for:

Receiving HTTP requests

Reading request parameters

Reading request bodies

Calling service methods

Returning API responses

Service Layer

Responsible for:

Business logic

Validation

Duplicate checks

Enrollment verification

Attendance verification

Calling repositories

Repository Layer

Responsible for:

Database operations

CRUD operations

Derived query methods

Spring Data JPA is used so standard CRUD operations do not require manually written SQL for every operation.

API Response Structure

Example successful response:

{
  "success": true,
  "data": {
    "id": 1,
    "name": "Ravi Kumar"
  },
  "error": null,
  "meta": null
}

Example paginated response:

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

Pagination

Pagination is used for listing APIs where required.

Example:

GET /api/teachers?page=0&size=10

Parameter

Description

Default

page

Page number

0

size

Number of records per page

10

Spring Data classes used:

Pageable

PageRequest

Page

Exception Handling

The application uses custom exceptions and centralized exception handling.

Examples include:

Resource not found

Duplicate resource

Bad request

Validation errors

Student not enrolled

Duplicate attendance

Duplicate teacher-batch assignment

Global exception handling is implemented using @RestControllerAdvice.

Data Validation and Business Rules

Teacher

Prevent duplicate email

Prevent duplicate phone number

Verify teacher exists before update

Teacher-Batch Assignment

Prevent duplicate teacher-batch assignment

Enrollment

Prevent duplicate student-batch enrollment

Maintain enrollment status

Attendance

Verify active student enrollment

Prevent duplicate attendance for the same student, batch, and date

ID Strategy

The application uses Long IDs for the main entities.

Teacher ID      → Long
Batch ID        → Long
Subject ID      → Long
Student ID      → Long
Attendance ID   → Long

Example identity generation:

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

Getting Started

Prerequisites

Install:

Java 17
MySQL
Maven
Git
IntelliJ IDEA

Clone the Repository

git clone https://github.com/Nikithayyadav/Tuition-management-system.git
cd Tuition-management-system

Database Setup

Create a MySQL database for the project.

Example:

CREATE DATABASE tuition_db;

Configure the datasource in the Spring Boot configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/tuition_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

Replace YOUR_PASSWORD with your MySQL password and use the actual database name configured in the project if it differs from this example.

Running the Application

Make sure MySQL is running, then start the Spring Boot application.

Windows

mvnw.cmd spring-boot:run

Linux / macOS

./mvnw spring-boot:run

Swagger UI

After starting the application, Swagger UI can be used to test the REST APIs.

http://localhost:8080/swagger-ui/index.html

Swagger can be used to:

View APIs

Enter request data

Execute APIs

View responses

Test successful scenarios

Test failure scenarios

Complete Testing Flow

Step 1 — Create Teacher

POST /api/teachers

Example:

{
  "name": "Ravi Kumar",
  "phone": "9876543210",
  "email": "ravi@example.com",
  "specialization": "Java"
}

Step 2 — Create Batch

POST /api/batches

Example:

{
  "name": "MRG_BATCH",
  "startTime": "09:00",
  "endTime": "11:00"
}

Step 3 — Create Subject

POST /api/subjects

Example:

{
  "name": "Java",
  "description": "Core Java programming"
}

Step 4 — Create Student

Use the Student Management APIs to create a student.

Step 5 — Enroll Student

POST /students/{studentId}/enrollment

Example:

{
  "batchId": 1
}

Step 6 — Assign Teacher to Batch

POST /api/teacher-batches?teacherId=1&batchId=1

Step 7 — Mark Attendance

POST /api/attendance

Example:

{
  "teacherId": 1,
  "batchId": 1,
  "studentId": 1,
  "date": "2026-09-16",
  "present": true
}

Step 8 — Manage Fees

Create Fee Structure
        ↓
Record Fee Payment
        ↓
Check Pending Fees
        ↓
Generate Fee Receipt

Step 9 — Manage Performance

Enter Examination Marks
        ↓
Calculate Percentage
        ↓
Calculate Grade
        ↓
Generate Performance Report

Testing Scenarios

Teacher

Add teacher

Get teachers

Update teacher

Test duplicate email

Test duplicate phone

Test update with non-existing teacher

Test pagination

Batch

Create batch

Get batches

Test pagination

Subject

Create subject

Get subjects

Test pagination

Teacher-Batch

Assign teacher to batch

View assignments

Prevent duplicate assignment

Student Enrollment

Enroll student into batch

Prevent duplicate enrollment

Maintain enrollment status

Attendance

Mark student present

Mark student absent

Verify active enrollment

Prevent duplicate attendance

Fees

Create fee structure

Record payment

Check pending fees

Generate fee receipt

Performance

Enter examination marks

Calculate percentage

Calculate grade

Generate performance report

Core Java Concepts Demonstrated

Phase 1 — Object-Oriented Programming

Classes and Objects

Constructors

Encapsulation

Inheritance

Polymorphism

Interfaces

Phase 2 — Collections and Exception Handling

ArrayList

HashMap

HashSet

Exception Handling

Custom Exceptions

Phase 3 — Java Functional Programming

Lambda Expressions

Stream API

filter()

map()

sorted()

collect()

groupingBy()

Phase 4 — Database and Persistence

JDBC concepts

MySQL

CRUD operations

JPA

Hibernate

Transactions

Batch Configuration

MRG_BATCH
09:00 AM - 11:00 AM

AFN_BATCH
01:00 PM - 03:00 PM

EVNG_BATCH
05:00 PM - 07:00 PM

The batch stores the start and end time, so a separate schedule entity is not required for these fixed batch timings.

Key Business Rules

A student can be enrolled in a batch.

Duplicate student-batch enrollment should not be allowed.

A teacher can be assigned to a batch.

Duplicate teacher-batch assignments should not be allowed.

Attendance can only be marked for a student who is actively enrolled in that batch.

Attendance should not be marked more than once for the same student, batch, and date.

Teacher email and phone numbers should not be duplicated.

Listing APIs support pagination where required.

API responses follow a common response structure.

The modules work together using shared database records.

Project Objectives

Digitize tuition institute management

Reduce manual record keeping

Centralize student information

Manage teachers and batches

Manage student enrollment

Manage teacher-batch assignments

Manage subjects

Track student attendance

Manage fees and payments

Track academic performance

Calculate student grades

Provide structured REST APIs

Maintain consistency between modules

Future Enhancements

Authentication and authorization

Role-based access control

Admin dashboard

Teacher dashboard

Student dashboard

Online fee payment

Email and SMS notifications

Attendance reports

Performance analytics

PDF fee receipts

Automated student reports

Docker deployment

Cloud deployment

Team Responsibilities

Student Management — Abhilash

Student management

Student enrollment

Student batch assignment

Teacher & Class Management — Nikitha

Teacher management

Batch management

Teacher-batch assignment

Subject management

Attendance management

Fees & Performance — Sadhvika

Fee structure

Fee payments

Pending fees

Fee receipts

Examination marks

Percentage calculation

Grade calculation

Performance reports

Project Status

Student Management          ✓
Student Enrollment          ✓

Teacher Management          ✓
Batch Management            ✓
Teacher-Batch Assignment    ✓
Subject Management          ✓
Attendance Management       ✓

Fee Structure               ✓
Fee Payment                 ✓
Performance Management      ✓

The Tuition Management System is a collaborative Spring Boot project containing three integrated modules that work together through shared entities and database records.

Repository

GitHub Repository: https://github.com/Nikithayyadav/Tuition-management-system

License

This project was developed as an academic project for learning and demonstrating Java, Spring Boot, REST APIs, database management, JPA, Hibernate, and software development concepts.
