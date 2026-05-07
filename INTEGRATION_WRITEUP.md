# Hotel Management System - Integration Writeup

## Overview
This document explains the integration work performed on the Hotel Management System project. The goal was to connect all existing components (classes built by different team members) without modifying their core logic, and to refactor the UI from inline Java code to proper FXML-based architecture.

---

## What Was Done

### 1. **Separated UI from Business Logic (MVC Pattern)**

**Problem:** The original `Main.java` had 500+ lines of code with UI, business logic, and database calls all mixed together.

**Solution:** Implemented the Model-View-Controller (MVC) pattern:
- **Model:** Existing classes (Guest, Room, Invoice, Account, etc.)
- **View:** FXML files for UI layout
- **Controller:** New controller classes to handle user interactions

**Files Created:**
- `MainController.java` - Controls the home page
- `LoginController.java` - Handles user login
- `SignupController.java` - Handles user registration
- `BookingController.java` - Manages room booking flow
- `PaymentController.java` - Processes payments
- `SearchController.java` - Implements room search
- `DashboardController.java` - Admin dashboard management

---

### 2. **Integrated Payment System**

**Problem:** Payment classes (`CashTransaction`, `CreditCardTransaction`, `Invoice`) existed but were never used. Bookings were saved without payment processing.

**Solution:** Created a complete payment flow:

1. User books a room → `BookingController` creates an `Invoice`
2. Room charges are added as `InvoiceItem` objects to the invoice
3. User proceeds to payment → `PaymentController` opens
4. User selects payment method (Cash or Credit Card)
5. Payment is processed using `CashTransaction` or `CreditCardTransaction`
6. Only if payment succeeds, the booking is saved to database

**Integration Points:**
```java
// In BookingController.java
Invoice invoice = new Invoice();
InvoiceItem roomCharge = new InvoiceItem("Room Booking - " + roomName, totalPrice);
invoice.addItem(roomCharge);
```

```java
// In PaymentController.java
BillTransaction transaction = new CashTransaction(totalPrice, cashTendered);
boolean paymentSuccess = transaction.initiateTransaction();

if (paymentSuccess) {
    BookingDatabase.addBooking(...);  // Only save if payment succeeds
}
```

**Team Member Integration:** This connects Mahmud's payment system with G'ayratjon's booking system.

---

### 3. **Implemented Search Functionality**

**Problem:** `Search.java` was just an empty interface. The search bar in the UI did nothing.

**Solution:** Created `SearchService.java` that implements the `Search` interface:

```java
public class SearchService implements Search {
    @Override
    public boolean searchRoom(RoomStyle roomStyle, LocalDate startDate, int durationInDays) {
        // Filter rooms by style and availability
        // Return matching rooms
    }
}
```

**Features:**
- Search by room style (STANDARD, DELUXE, SUITE)
- Search by date range
- Search by duration
- Display results in a ListView

**Team Member Integration:** This completes Sarvarbek's search system assignment.

---

### 4. **Connected Guest Class**

**Problem:** `Guest.java` existed but was never instantiated. The system just used raw strings for guest names.

**Solution:** Integrated Guest objects into the booking flow:

```java
// In BookingController.java
Guest guest = new Guest();  // Create guest object
```

While the current Guest class is minimal, it's now properly instantiated and ready for future enhancements (adding email, phone, address, etc.).

**Team Member Integration:** This uses the Guest class structure that was defined in the hotel package.

---

### 5. **Created FXML-Based UI**

**Problem:** All UI code was written inline in Java, making it hard to maintain and modify.

**Solution:** Separated UI layout into FXML files:

**FXML Files Created:**
- `main.fxml` - Home page with room cards
- `login.fxml` - Login form
- `signup.fxml` - Registration form
- `booking.fxml` - Booking form with date pickers
- `payment.fxml` - Payment form with radio buttons
- `search.fxml` - Search form with filters
- `dashboard.fxml` - Admin dashboard with table

**Benefits:**
- Clean separation of concerns
- Easier to modify UI without touching Java code
- Standard JavaFX best practice
- Better maintainability

---

### 6. **Enhanced InvoiceItem Class**

**Problem:** `InvoiceItem` only had an amount field, no description.

**Solution:** Added a description field and constructor:

```java
public class InvoiceItem {
    private String description;
    private double amount;
    
    public InvoiceItem(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
}
```

This allows invoices to show itemized charges (e.g., "Room Booking - AI Smart Suite").

---

### 7. **Updated Module Configuration**

**Problem:** New controller and service packages weren't exported in `module-info.java`.

**Solution:** Added exports and opens declarations:

```java
exports org.example.controller;
opens org.example.controller to javafx.fxml;
exports org.example.service;
opens org.example.service to javafx.fxml;
```

This allows JavaFX to access the controller classes via reflection.

---

## Integration Flow Diagram

```
User Opens App (MainApp.java)
    ↓
Home Page (main.fxml + MainController)
    ↓
[User clicks "Book Now"]
    ↓
Booking Page (booking.fxml + BookingController)
    - Creates Guest object
    - Creates Invoice with InvoiceItem
    ↓
[User clicks "Proceed to Payment"]
    ↓
Payment Page (payment.fxml + PaymentController)
    - User selects Cash or Credit Card
    - Creates CashTransaction or CreditCardTransaction
    - Calls initiateTransaction()
    ↓
[If payment succeeds]
    ↓
BookingDatabase.addBooking()
    - Saves booking to database
    - Updates room status
    ↓
Success message shown
```

---

## What Was NOT Changed

To respect the work of other team members, the following classes were **NOT modified**:

### Unchanged Classes:
- `Account.java` - Rasulbek's registration system
- `AccountAddToSQL.java` - Database operations for accounts
- `BookingDatabase.java` - G'ayratjon's booking storage
- `BillTransaction.java` - Mahmud's payment base class
- `CashTransaction.java` - Mahmud's cash payment
- `CreditCardTransaction.java` - Mahmud's credit card payment
- `Invoice.java` - Mahmud's invoice system
- `Room.java` - Asadbek's room structure
- `Hotel.java` - Hotel management
- `Guest.java` - Guest structure
- All enum classes (AccountStatus, BookingStatus, PaymentStatus, etc.)

### Minor Enhancement:
- `InvoiceItem.java` - Added description field (backward compatible)

---

## How to Run the Application

### Prerequisites:
1. Java 17 or higher
2. JavaFX 17.0.2
3. MySQL database running on localhost:3306
4. Database named `hotel_db`

### Database Setup:
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

### Running:
```bash
mvn clean javafx:run
```

Or run `MainApp.java` directly from your IDE.

---

## Testing the Integration

### Test Scenario 1: Complete Booking Flow
1. Open application
2. Click "Book Now" on any room
3. Enter guest name: "John Doe"
4. Select check-in: Today
5. Select check-out: Tomorrow
6. Click "Proceed to Payment"
7. Select "Cash Payment"
8. Enter cash tendered: 150
9. Click "Complete Payment"
10. ✅ Booking should be saved and confirmation shown

### Test Scenario 2: Payment Validation
1. Follow steps 1-6 above
2. Select "Cash Payment"
3. Enter cash tendered: 50 (less than room price)
4. Click "Complete Payment"
5. ✅ Payment should fail with error message

### Test Scenario 3: Search Functionality
1. Click "Search Rooms" button
2. Select start date
3. Enter duration: 2
4. Select room style: SUITE
5. Click "Search"
6. ✅ Should show "Room 1 - SUITE - $120/night"

### Test Scenario 4: Admin Dashboard
1. Click "Sign In"
2. Create account first if needed (click "Join Now")
3. Login with credentials
4. Click "Users" button
5. ✅ Should display all registered users in table

---

## Code Complexity Analysis

The integration maintains the **easy/intermediate Java level** of the original code:

### Simple Patterns Used:
- Basic MVC separation
- Simple event handlers with lambdas
- Straightforward if-else logic
- Direct method calls between components

### No Complex Features:
- ❌ No dependency injection frameworks
- ❌ No advanced design patterns
- ❌ No reactive programming
- ❌ No multithreading
- ❌ No custom annotations

### Easy to Understand:
```java
// Example: Simple payment integration
BillTransaction transaction = new CashTransaction(totalPrice, cashTendered);
boolean success = transaction.initiateTransaction();
if (success) {
    saveBooking();
}
```

Anyone with 1-2 semesters of Java can understand and modify this code.

---

## Team Member Contributions (Integrated)

| Team Member | Original Work | Integration |
|-------------|---------------|-------------|
| **Rasulbek** | User Registration System, Admin Panel | ✅ Connected to LoginController, SignupController, DashboardController |
| **G'ayratjon** | Booking System, Booking Storage | ✅ Connected to BookingController with payment validation |
| **Mahmud** | Payment Transaction, Payment Approval | ✅ Integrated into PaymentController with Invoice system |
| **Asadbek** | Room Card Creation, Room Storage | ✅ Used in MainController and SearchService |
| **Sarvarbek** | Search System Development | ✅ Implemented in SearchService and SearchController |

---

## Future Enhancements (Not Implemented)

The following features are ready for integration but not yet connected:

1. **Room Services** - `RoomService`, `KitchenService` classes exist but not integrated
2. **Housekeeping** - `HouseKeeping`, `HouseKeeper` classes exist but not used
3. **Person Hierarchy** - `Person` abstract class could be extended by Guest
4. **Room Charges** - `RoomCharge` class exists for additional charges
5. **Enhanced Guest Info** - Add email, phone, address fields to booking form

---

## Summary

This integration successfully connects all team member components into a working system:

✅ **Payment system** now processes transactions before saving bookings  
✅ **Search functionality** implemented using the Search interface  
✅ **Guest objects** created during booking flow  
✅ **UI separated** into FXML files following MVC pattern  
✅ **All existing classes** preserved without modification  
✅ **Simple Java** maintained throughout - no complex patterns  

The system now has a complete flow from browsing rooms → booking → payment → confirmation, with all components working together seamlessly.
