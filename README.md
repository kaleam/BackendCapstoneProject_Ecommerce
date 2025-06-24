# 🛒 Backend Capstone Project — E-commerce Microservices

This repository contains the backend implementation for an **E-commerce Platform** developed as a Capstone Project. It is built using a **Microservices Architecture** with technologies like **Spring Boot**, **Kafka**, **MongoDB**, and **MySQL**, designed for scalability and maintainability.

---

- [🛒 Backend Capstone Project — E-commerce Microservices](#-backend-capstone-project--e-commerce-microservices)
  - [🚀 Features](#-features)
  - [🧱 Microservices Overview](#-microservices-overview)
  - [🧪 Tech Stack](#-tech-stack)
  - [📂 Project Structure](#-project-structure)
  - [🔧 Getting Started](#-getting-started)
    - [Prerequisites](#prerequisites)
    - [Running the Application](#running-the-application)
      - [Build the microservice](#build-the-microservice)
    - [Build and run microservice](#build-and-run-microservice)
  - [API Testing](#api-testing)
    - [Use postman collection 'Ecom.postman\_collection.json' to test endpoints](#use-postman-collection-ecompostman_collectionjson-to-test-endpoints)
    - [Swagger UI is also available for each microservice](#swagger-ui-is-also-available-for-each-microservice)
  - [🔐 Security](#-security)
  - [🧪 Testing](#-testing)
    - [Run unit tests](#run-unit-tests)
  - [📊 Monitoring](#-monitoring)
    - [Actuator endpoint](#actuator-endpoint)
  - [🧾 License](#-license)
      - [This project is licensed under the MIT License - see the LICENSE file for details.](#this-project-is-licensed-under-the-mit-license---see-the-license-file-for-details)
  - [🙋‍♂️ Contributors](#️-contributors)
      - [Abhijeet Kale (@kaleam)](#abhijeet-kale-kaleam)
  - [📬 Contact](#-contact)
      - [For any queries or suggesstions: mr.abhijeetkale@gmail.com](#for-any-queries-or-suggesstions-mrabhijeetkalegmailcom)

---

## 🚀 Features

- 🔐 **User Management** – Signup, Login, Logout, JWT-based Authentication, Session Tracking
- 🛍️ **Product Catalog** – Search and browse products using Elasticsearch
- 🛒 **Cart Management** – Add, remove, view items in cart and checkout
- 🧾 **Order Service** – Processing checkout, Order placement, Order tracking, Payment initiation
- 💳 **Payment Service** – Mock payment processing and transaction logging
- 🔔 **Notification Service** – Email/SMS notifications via Kafka
- 📦 **Load Balancer** – Centralized routing - HAProxy
- 📊 **Monitoring** – Actuator service exposed for each microservice

---

## 🧱 Microservices Overview

| Service         | Description                           | Technology Stack                  |
| --------------- | ------------------------------------- | --------------------------------- |
| User Service    | Manages users, login, sessions        | Spring Boot, MySQL, JWT, Bcrypt   |
| Product Service | Searchable product listings           | Spring Boot, Elasticsearch, MySQL |
| Cart Service    | Manages user carts                    | Spring Boot, MongoDB, Redis       |
| Order Service   | Handles order lifecycle               | Spring Boot, Kafka, MySQL         |
| Payment Service | Simulates payments                    | Spring Boot, Kafka, MySQL         |
| Notification    | Sends order and payment notifications | Spring Boot, Kafka                |
| Load Balancer   | Routes requests to microservices      | HAProxy                           |

---

## 🧪 Tech Stack

- **Java 21 + Spring Boot 3.4.4**
- **Kafka 2.13-4.0.0** for async communication
- **MySQL 8.0.37** & **MongoDB 8.0.9** for data storage
- **Elasticsearch 9.0.1** for product search
- **JUnit + Mockito** for testing
- **Actuator** for monitoring

---

## 📂 Project Structure

- **UserManagementService**
- **ProductCatalogService**
- **CartService**
- **OrderManagementService**
- **PaymentService**
- **NotificationService**
- **haproxy.cfg**
- **Ecom.postman_collection.json**
- **LICENSE**
- **README.md**
- **Scaler Neovarsity _ Academy Project Report (Backend Specialization).pdf**

---

## 🔧 Getting Started

### Prerequisites

- **Java 21+**
- **Kafka 2.13-4.0.0**
- **MySQL 8.0.37**
- **MongoDB 8.0.9**
- **Elasticsearch 9.0.1**
- **Maven 3.9.9**

### Running the Application

#### Build the microservice
```
mvn clean package
```

### Build and run microservice
```
mvn spring-boot:run
```

## API Testing

### Use postman collection 'Ecom.postman_collection.json' to test endpoints
### Swagger UI is also available for each microservice
- **UserManagementService**     - http://localhost:8080/api/user/swagger-ui/index.html#/
- **ProductCatalogService**     - http://localhost:8080/api/product/swagger-ui/index.html#/
- **CartService**               - http://localhost:8080/api/cart/swagger-ui/index.html#/
- **OrderManagementService**    - http://localhost:8080/api/order/swagger-ui/index.html#/
- **PaymentService**            - http://localhost:8080/api/payment/swagger-ui/index.html#/

## 🔐 Security

- **JWT token authentication via UserManagementService**
- **BCrypt for password hashing**

## 🧪 Testing

### Run unit tests
```
mvn test
```

## 📊 Monitoring

### Actuator endpoint

- **UserManagementService**     - http://localhost:8080/api/user/actuator/health
- **ProductCatalogService**     - http://localhost:8080/api/product/actuator/health
- **CartService**               - http://localhost:8080/api/cart/actuator/health
- **OrderManagementService**    - http://localhost:8080/api/order/actuator/health
- **PaymentService**            - http://localhost:8080/api/payment/actuator/health

## 🧾 License

#### This project is licensed under the MIT License - see the LICENSE file for details.

## 🙋‍♂️ Contributors

#### Abhijeet Kale (@kaleam)

## 📬 Contact

#### For any queries or suggesstions: mr.abhijeetkale@gmail.com