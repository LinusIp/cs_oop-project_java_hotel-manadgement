# Hotel Management System - Integrated Version

## Quick Start

### 1. Setup Database
```sql
CREATE DATABASE hotel_db;
USE hotel_db;

CREATE TABLE users (
    USER_ID INT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL
);

CREATE TABLE rooms (
    room_id INT PRIMARY KEY,
    room_name VARCHAR(100),
    price DOUBLE,
    status VARCHAR(20)
);

CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(100),
    room_id INT,
    check_in DATE,
    check_out DATE,
    total_price DOUBLE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);
```

### 2. Update Database Connection
Edit `src/main/java/org/example/registration/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password";  // Update this
```

### 3. Run Application
```bash
mvn clean javafx:run
```

Or run `MainApp.java` from your IDE.

---

## Features

### ✅ User Authentication
- Sign up with username/password
- Login to access admin dashboard

### ✅ Room Booking
- Browse 3 available rooms
- Select check-in/check-out dates
- Automatic price calculation

### ✅ Payment Processing
- Cash payment option
- Credit card payment option
- Payment validation before booking

### ✅ Search Functionality
- Search by room style
- Search by date range
- Filter by duration

### ✅ Admin Dashboard
- View all registered users
- Manage bookings
- Access room information

---

## Project Structure

```
src/main/java/org/example/
├── MainApp.java                    # Application entry point
├── controller/                     # UI Controllers (NEW)
│   ├── MainController.java
│   ├── LoginController.java
│   ├── SignupController.java
│   ├── BookingController.java
│   ├── PaymentController.java
│   ├── SearchController.java
│   └── DashboardController.java
├── service/                        # Business Logic (NEW)
│   └── SearchService.java
├── hotel/                          # Hotel Domain (EXISTING)
│   ├── Hotel.java
│   ├── Room.java
│   ├── Guest.java
│   ├── BookingDatabase.java
│   └── InvoiceItem.java
├── payment_system/                 # Payment Domain (EXISTING)
│   ├── Invoice.java
│   ├── BillTransaction.java
│   ├── CashTransaction.java
│   └── CreditCardTransaction.java
├── registration/                   # User Management (EXISTING)
│   ├── Account.java
│   ├── AccountAddToSQL.java
│   └── DatabaseConnection.java
└── enums/                          # Status Enums (EXISTING)
    ├── AccountStatus.java
    ├── BookingStatus.java
    ├── PaymentStatus.java
    └── RoomStatus.java

src/main/resources/
├── fxml/                           # UI Layouts (NEW)
│   ├── main.fxml
│   ├── login.fxml
│   ├── signup.fxml
│   ├── booking.fxml
│   ├── payment.fxml
│   ├── search.fxml
│   └── dashboard.fxml
└── images/
    ├── logo.png
    ├── room1.png
    ├── room2.png
    └── room3.png
```

---

## What Changed?

### ✅ Integration Work
- Connected payment system to booking flow
- Implemented search functionality
- Separated UI into FXML files
- Created controller classes for MVC pattern
- Integrated Guest objects into bookings

### ❌ Preserved Original Code
- All domain classes unchanged
- Payment logic unchanged
- Database operations unchanged
- Enum definitions unchanged

See `INTEGRATION_WRITEUP.md` for detailed explanation.

---

## Technologies

- Java 17
- JavaFX 17.0.2
- MySQL 8.0
- Maven

---

## Team

- **Rasulbek** - User Registration & Admin Panel
- **G'ayratjon** - Booking System
- **Mahmud** - Payment Processing
- **Asadbek** - Room Management
- **Sarvarbek** - Search System
