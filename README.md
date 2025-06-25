# 🚗 Smart Parking Management System

## 📘 Project Description
This project is a **cloud-native smart parking management system** designed using Spring Boot microservices architecture. It enables users to search for available parking spaces, register their vehicles, make reservations, and perform secure payments. Admins can manage zones, monitor bookings, and control availability. All services are independently deployable and communicate via REST APIs.

---

## ✨ Features

### 👤 User Features
- 🔐 **User Registration & Login** (JWT/Session-based)
- 📍 **Real-time Parking Availability**
- 🅿️ **Reserve Parking Slots** for Cars, Bikes, or Buses
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

| Feature              | Screenshot |
|----------------------|------------|
| 🧭 Eureka Dashboard   | ![Eureka Dashboard](./docs/screenshots/eureka_dashboard.png) |
| 🧪 Postman Testing    | ![Postman](https://via.placeholder.com/600x400.png?text=Postman+Collection) |
| 🚗 Vehicle Form       | ![Vehicle Form](https://via.placeholder.com/600x400.png?text=Vehicle+Registration) |
| 🅿️ Reserve Slot       | ![Reserve](https://via.placeholder.com/600x400.png?text=Reserve+Parking+Slot) |
| 🛠️ Admin Panel        | ![Admin](https://via.placeholder.com/600x400.png?text=Zone+Management) |
| 💳 Payment Receipt    | ![Receipt](https://via.placeholder.com/600x400.png?text=Payment+Receipt) |


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

Special thanks to the instructors, mentors, and teammates who provided guidance and support throughout the development of this system.



