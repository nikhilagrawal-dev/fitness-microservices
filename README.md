# 🏋️ Fitness Tracking Microservices

An AI-powered Fitness Tracking System built using **Spring Boot Microservices**, **Spring Cloud**, **Keycloak**, **Kafka**, **MongoDB**, **PostgreSQL**, and **Google Gemini AI**.

The project demonstrates a production-style microservices architecture with centralized configuration, service discovery, authentication, API gateway, asynchronous communication, and AI-powered fitness recommendations.

---

# 🚀 Features

- 🔐 Keycloak Authentication & Authorization
- 🌐 Spring Cloud API Gateway
- 🔍 Eureka Service Discovery
- ⚙️ Spring Cloud Config Server
- 👤 User Management Service
- 🏃 Activity Tracking Service
- 🤖 AI Recommendation Service
- 📨 Kafka Event Streaming
- 🗄 PostgreSQL for User Data
- 🍃 MongoDB for Activities & Recommendations
- 🔄 RESTful APIs
- 🧩 Distributed Microservices Architecture

---

# 🏗️ System Architecture

```text
                                      ┌──────────────────────┐
                                      │        Client        │
                                      │ (Web / Mobile App)   │
                                      └──────────┬───────────┘
                                                 │
                                                 ▼
                               ┌────────────────────────────────┐
                               │      Spring Cloud Gateway      │
                               │  JWT Validation • Routing      │
                               │  Keycloak User Synchronization │
                               └───────────────┬────────────────┘
                                               │
                    ┌──────────────────────────┼──────────────────────────┐
                    │                          │                          │
                    ▼                          ▼                          ▼
        ┌──────────────────┐      ┌──────────────────┐      ┌──────────────────┐
        │   User Service   │      │ Activity Service │      │    AI Service    │
        │                  │      │                  │      │                  │
        │ User Management  │      │ Activity CRUD    │      │ Recommendations  │
        │ Validation APIs  │      │ Kafka Producer   │      │ Kafka Consumer   │
        └────────┬─────────┘      └────────┬─────────┘      └────────┬─────────┘
                 │                         │                         │
                 │                         │                         │
                 ▼                         ▼                         ▼
         ┌──────────────┐         ┌──────────────┐          ┌──────────────┐
         │ PostgreSQL   │         │   MongoDB    │          │   MongoDB    │
         │ User Data    │         │ Activities   │          │Recommendations│
         └──────────────┘         └──────────────┘          └──────────────┘
                                            │
                                            │ Publish Event
                                            ▼
                                   ┌──────────────────┐
                                   │      Kafka       │
                                   │ Event Streaming  │
                                   └────────┬─────────┘
                                            │
                                            ▼
                                   ┌──────────────────┐
                                   │ Google Gemini AI │
                                   │ Recommendation   │
                                   └──────────────────┘


═══════════════════════════════════════════════════════════════════════════════

                    Shared Infrastructure (Used by All Services)

        ┌────────────────────┐
        │  Config Server     │
        │ Centralized Config │
        └─────────┬──────────┘
                  │
                  ▼
        ┌────────────────────┐
        │ Eureka Discovery   │
        │ Service Registry   │
        └─────────┬──────────┘
                  │
                  ▼
        ┌────────────────────┐
        │     Keycloak       │
        │ OAuth2 / JWT Auth  │
        └────────────────────┘
```
---

# 🛠 Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Cloud
- Spring Security
- Spring WebFlux
- Spring Data JPA
- Spring Data MongoDB

### Authentication

- Keycloak
- OAuth2
- JWT

### Databases

- PostgreSQL
- MongoDB

### Messaging

- Apache Kafka

### AI

- Google Gemini API

### Build Tool

- Maven

### Tools

- IntelliJ IDEA
- Docker
- Postman
- Git
- GitHub

---

# 📂 Microservices

## Config Server

Responsible for centralized configuration management.

Port

```
8888
```

---

## Eureka Server

Service Discovery Server.

Port

```
8761
```

---

## API Gateway

Responsibilities

- JWT Validation
- Keycloak Authentication
- User Synchronization
- Request Routing

Port

```
8080
```

---

## User Service

Stores user information.

Database

```
PostgreSQL
```

Port

```
8081
```

---

## Activity Service

Stores workout activities.

Database

```
MongoDB
```

Communicates using Kafka.

---

## AI Service

Generates AI-powered fitness recommendations using Google Gemini.

Stores recommendations in MongoDB.

---

# 🗄 Databases

## PostgreSQL

Stores

- Users
- User Profile

---

## MongoDB

Stores

- Activities
- AI Recommendations

---

# 🔄 Request Flow

```
User
 │
 ▼
Gateway
 │
 ▼
Keycloak Authentication
 │
 ▼
User Validation
 │
 ▼
Activity Service
 │
 ▼
Kafka Event
 │
 ▼
AI Service
 │
 ▼
Gemini API
 │
 ▼
Recommendation Stored
```

---

# 📦 Running the Project

Clone the repository

```bash
git clone https://github.com/nikhilagrawal-dev/fitness-microservices.git
```

Move inside

```bash
cd fitness-microservices
```

Start

- PostgreSQL
- MongoDB
- Kafka
- Keycloak

Run

- Config Server
- Eureka Server
- Gateway
- User Service
- Activity Service
- AI Service

---

# 📡 API Overview

## User Service

- Register User
- Validate User
- Get User

---

## Activity Service

- Create Activity
- Get Activities

---

## AI Service

- Generate Recommendation
- Get Recommendations

---

# 📌 Future Improvements

- Docker Compose
- Kubernetes Deployment
- Redis Cache
- Prometheus Monitoring
- Grafana Dashboard
- Circuit Breaker
- Distributed Tracing
- CI/CD Pipeline
- Unit Testing
- Integration Testing

---

# 📚 Learning Outcomes

This project demonstrates

- Spring Boot Microservices
- Spring Cloud Gateway
- Eureka Discovery
- Config Server
- OAuth2 Authentication
- JWT Validation
- Keycloak Integration
- Kafka Messaging
- PostgreSQL
- MongoDB
- REST APIs
- AI Integration with Gemini
- Distributed System Design

---

# 👨‍💻 Author

**Nikhil Agrawal**

GitHub

https://github.com/nikhilagrawal-dev

---

## ⭐ If you found this project useful, consider giving it a star.
