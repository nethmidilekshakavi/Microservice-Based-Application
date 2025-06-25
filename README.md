# 🚗 Smart Parking Management System

## 📘 Project Description
This project is a **cloud-native smart parking management system** designed using Spring Boot microservices architecture. It enables users to search for available parking spaces, register their vehicles, make reservations, and perform secure payments. Admins can manage zones, monitor bookings, and control availability. All services are independently deployable and communicate via REST APIs.

---

## ✨ Features

### 👤 User Features
- 🔐 **User Registration & Login** (JWT/Session-based)
- 📍 **Real-time Parking Availability**
- 🅿️ **Reserve Parking Slots**
- 💳 **Online Payment Integration**
- 📖 **View Booking History**
- 🚘 **Manage Multiple Vehicles**

### 🛠️ Admin Features
- 🗺️ **Zone Management**
- 📊 **Booking and User Monitoring**
- 👥 **User Account Management**
- 🧾 **Payment Monitoring & Receipts**

---

## 🧰 Technologies Used

- ☕ **Backend**: Spring Boot, Spring Cloud (Eureka, Gateway, Config)
- 💾 **Database**: MySQL / H2, JPA/Hibernate
- 🧪 **Testing**: Postman
- 📡 **Monitoring**: Spring Actuator, Eureka Dashboard
- 🔐 **Security**: Spring Security (JWT/Basic Auth)
- 📁 **Version Control**: Git & GitHub
- 🧱 **Build Tool**: Maven

---

![Eureka DashBoard](screenShot/Screenshot%202025-06-25%20184837.png)

## 📦 Postman Collection

All REST APIs are tested using Postman. You can import the collection below:

📎 [Download Postman Collection](Smart%20Parking%20Management%20System.postman_collection.json)

---

## 🏗️ Architecture

| 🧩 Microservice       | 📝 Description                      | 🔢 Port |
|------------------------|------------------------------------|--------|
| 🌐 **Eureka Server**   | Service Discovery Registry         | `8761` |
| 🔁 **API Gateway**     | Request Routing & Filtering        | `8080` |
| 👤 **User Service**    | Auth, Registration, Profile Mgmt   | `8084` |
| 🅿️ **Parking Service** | Zone & Slot Management             | `8082` |
| 🚘 **Vehicle Service** | Vehicle Tracking                   | `8081` |
| 💳 **Payment Service** | Payments, Invoices, Receipts       | `8083` |

---

## 🚀 Getting Started

### 📋 Prerequisites
- ✅ Java 17
- ✅ Maven 3.6+
- ✅ MySQL (or use H2 for development)
- ✅ IntelliJ IDEA or similar IDE
- ✅ Postman

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

Heartfelt thanks to the instructors whose guidance and support were instrumental in the successful development of this system.


