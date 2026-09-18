# Employee Leave Management System

Backend REST API for managing employees, leave requests, approvals and leave balances.

## Technology Stack
- Java 17
- Spring Boot 3.5.6
- Spring Web / REST
- Spring Data JPA and Hibernate
- MySQL
- Maven
- Bean Validation
- Postman

## Architecture
Controller -> Service -> Repository -> MySQL

## Features
- Employee CRUD
- Automatic initial leave balance: Casual 10, Sick 10, Earned 15
- Apply leave with date validation
- Approve, reject and cancel requests
- Balance validation before approval
- Global exception handling
- RESTful API design

## Setup
1. Install Java 17, Maven and MySQL.
2. Run database/schema.sql, or create database employee_leave_db.
3. Open src/main/resources/application.properties.
4. Replace YOUR_MYSQL_PASSWORD with your local MySQL password.
5. Run: mvn spring-boot:run
6. API base URL: http://localhost:8080

## APIs
POST /api/employees - Create employee
GET /api/employees - List employees
GET /api/employees/{id} - Get employee
PUT /api/employees/{id} - Update employee
DELETE /api/employees/{id} - Delete employee
GET /api/employees/{id}/balance - Get leave balance
POST /api/leaves/employee/{employeeId} - Apply leave
GET /api/leaves - List leave requests
GET /api/leaves/{id} - Get leave request
GET /api/leaves/employee/{employeeId} - Employee leave history
PUT /api/leaves/{id}/approve - Approve leave
PUT /api/leaves/{id}/reject - Reject leave
PUT /api/leaves/{id}/cancel - Cancel leave

## Sample employee request
{"name":"Rahul Kumar","email":"rahul@example.com","department":"IT"}

## Sample leave request
{"leaveType":"CASUAL","startDate":"2026-10-05","endDate":"2026-10-07","reason":"Personal work"}

## Resume Description
Employee Leave Management System — Developed a RESTful backend using Java and Spring Boot to manage employees, leave requests and leave balances. Implemented CRUD APIs, JPA/Hibernate persistence with MySQL, validation, exception handling and approval workflows.