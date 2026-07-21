# 🏋️ Fitness Tracker Microservices

A full-stack Fitness Tracker application built using **Spring Boot Microservices**, **React**, **Keycloak OAuth2**, **Kafka**, **PostgreSQL**, and **MongoDB**.

This project demonstrates a modern microservices architecture with secure authentication, asynchronous messaging, and AI-powered fitness recommendations.

---

## 🚀 Features

- 🔐 OAuth2 Authentication using Keycloak
- 👤 User Registration & Login
- 🏃 Track Fitness Activities
- 📊 View Activity History
- 🤖 AI-Based Activity Recommendations
- 📨 Event-Driven Communication using Kafka
- 🌐 API Gateway
- 🔍 Service Discovery using Eureka
- ⚙️ Centralized Configuration using Config Server
- 💾 PostgreSQL & MongoDB Integration

---

# 🏗️ Architecture

```
                    +----------------+
                    |     React      |
                    |  Frontend      |
                    +-------+--------+
                            |
                            |
                    +-------v--------+
                    |  API Gateway   |
                    +-------+--------+
                            |
          ------------------------------------
          |                 |                |
          |                 |                |
+---------v------+ +--------v-------+ +------v------+
| User Service   | | ActivityService| | AI Service  |
+---------+------+ +--------+-------+ +------+------+
          |                 |                 |
          |                 |                 |
     PostgreSQL         MongoDB          Kafka Consumer
                              |
                              |
                          Kafka Broker

                    +--------------------+
                    | Config Server      |
                    +--------------------+

                    +--------------------+
                    | Eureka Server      |
                    +--------------------+

                    +--------------------+
                    | Keycloak Server    |
                    +--------------------+
```

---

# 🛠️ Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Cloud Gateway
- Spring Cloud Config
- Spring Cloud Eureka
- Spring Data JPA
- Spring Data MongoDB
- Kafka
- Maven

### Frontend

- React
- Material UI
- Redux Toolkit
- Axios
- React OAuth2 PKCE

### Databases

- PostgreSQL
- MongoDB

### Authentication

- Keycloak
- OAuth2
- OpenID Connect
- PKCE

### DevOps

- Docker
- Docker Compose

---

# 📂 Project Structure

```
fitness-microservices/

├── activityservice
├── userservice
├── gateway
├── configserver
├── eureka
├── aiservice
├── fitness-frontend
└── README.md
```

---

# ⚙️ Running the Project

## Clone Repository

```bash
git clone https://github.com/nikhilagrawal-dev/fitness-microservices.git
```

```
cd fitness-microservices
```

---

## Start Infrastructure

- PostgreSQL
- MongoDB
- Kafka
- Keycloak

---

## Start Services

1. Config Server
2. Eureka Server
3. User Service
4. Activity Service
5. AI Service
6. API Gateway
7. React Frontend

---

# 🔐 Authentication Flow

- User clicks Login
- Redirected to Keycloak
- OAuth2 Authorization Code + PKCE
- JWT Access Token issued
- Token attached to every API request
- Gateway validates token
- Services authorize request

---

# 📸 Screenshots

### Login

<img width="966" height="549" alt="Screenshot 2026-07-21 at 3 08 09 PM" src="https://github.com/user-attachments/assets/f4894840-d979-4909-8603-72dcd5a0c149" />


### Dashboard

<img width="1710" height="672" alt="Screenshot 2026-07-21 at 3 11 14 PM" src="https://github.com/user-attachments/assets/0d36f1dd-4fa5-4e9e-ba0f-f2a796346c40" />



### AI Recommendations , Improvements


<img width="777" height="927" alt="Screenshot 2026-07-21 at 3 11 45 PM" src="https://github.com/user-attachments/assets/ff39b7d7-2773-41d8-961b-377a6ec78886" />

---

# Future Improvements

- Dashboard Analytics
- Charts
- Calories Graph
- Weekly Statistics
- Docker Compose One Click Run
- Kubernetes Deployment
- CI/CD Pipeline
- Unit & Integration Testing

---

# 👨‍💻 Author

**Nikhil Agrawal**

B.Tech CSE (AI & DS)

Ramdeobaba University, Nagpur

GitHub:
https://github.com/nikhilagrawal-dev
