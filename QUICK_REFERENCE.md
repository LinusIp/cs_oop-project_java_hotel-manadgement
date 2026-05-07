# Quick Reference Guide

## File Structure Overview

### New Files Created (Integration Work)

```
Controllers (7 files):
├── MainController.java         - Home page with room cards
├── LoginController.java        - User login
├── SignupController.java       - User registration
├── BookingController.java      - Room booking form
├── PaymentController.java      - Payment processing
├── SearchController.java       - Room search
└── DashboardController.java    - Admin panel

FXML Views (7 files):
├── main.fxml                   - Home page layout
├── login.fxml                  - Login form
├── signup.fxml                 - Signup form
├── booking.fxml                - Booking form
├── payment.fxml                - Payment form
├── search.fxml                 - Search form
└── dashboard.fxml              - Dashboard layout

Service Layer (1 file):
└── SearchService.java          - Implements Search interface

Entry Point (1 file):
└── MainApp.java                - Application launcher

Documentation (4 files):
├── README.md                   - Quick start guide
├── INTEGRATION_WRITEUP.md      - Detailed explanation
├── INTEGRATION_DIAGRAM.md      - Visual diagrams
└── QUICK_REFERENCE.md          - This file
```

---

## Key Integration Points

### 1. Payment Integration
**Location:** `PaymentController.java` lines 60-85

```java
// Create transaction based on payment method
BillTransaction transaction;
if (cashRadio.isSelected()) {
    transaction = new CashTransaction(totalPrice, cashTendered);
} else {
    transaction = new CreditCardTransaction(totalPrice, cardName, zipCode);
}

// Process payment
boolean success = transaction.initiateTransaction();

// Only save booking if payment succeeds
if (success) {
    BookingDatabase.addBooking(...);
}
```

**Connects:** Mahmud's payment system → G'ayratjon's booking system

---

### 2. Invoice Integration
**Location:** `BookingController.java` lines 70-75

```java
// Create invoice with room charges
Invoice invoice = new Invoice();
InvoiceItem roomCharge = new InvoiceItem("Room Booking - " + roomName, totalPrice);
invoice.addItem(roomCharge);
```

**Connects:** Mahmud's invoice system → Booking flow

---

### 3. Search Integration
**Location:** `SearchService.java` lines 20-35

```java
@Override
public boolean searchRoom(RoomStyle roomStyle, LocalDate startDate, int durationInDays) {
    List<Room> matchingRooms = new ArrayList<>();
    for (Room room : availableRooms) {
        if (room.getStyle() == roomStyle && room.getRoomStatus() == RoomStatus.AVAILABLE) {
            matchingRooms.add(room);
        }
    }
    return !matchingRooms.isEmpty();
}
```

**Connects:** Sarvarbek's Search interface → Asadbek's Room class

---

### 4. Guest Integration
**Location:** `BookingController.java` line 68

```java
// Create Guest object
guest = new Guest();
```

**Connects:** Guest class → Booking flow

---

### 5. Authentication Integration
**Location:** `LoginController.java` lines 30-45

```java
if (accountService.isValidLogin(username, password)) {
    currentStage.close();
    openDashboard();
}
```

**Connects:** Rasulbek's Account system → Dashboard access

---

## How Each Component Works

### MainController
- **Purpose:** Controls home page
- **Key Methods:**
  - `handleRoom1Booking()` - Opens booking for Room 1
  - `handleRoom2Booking()` - Opens booking for Room 2
  - `handleRoom3Booking()` - Opens booking for Room 3
  - `openLoginWindow()` - Opens login dialog
  - `openSignupWindow()` - Opens signup dialog
  - `openSearchWindow()` - Opens search dialog

### BookingController
- **Purpose:** Manages booking process
- **Key Methods:**
  - `setRoomDetails()` - Receives room info from MainController
  - `updateTotal()` - Calculates total price based on dates
  - `handleProceedToPayment()` - Creates Guest, Invoice, opens PaymentController
- **Integration:** Creates Guest object, Invoice with InvoiceItem

### PaymentController
- **Purpose:** Processes payments
- **Key Methods:**
  - `setPaymentDetails()` - Receives booking info from BookingController
  - `handlePayment()` - Creates transaction, processes payment, saves booking
- **Integration:** Uses CashTransaction/CreditCardTransaction, calls BookingDatabase

### SearchController
- **Purpose:** Handles room search
- **Key Methods:**
  - `handleSearch()` - Gets criteria, calls SearchService, displays results
- **Integration:** Uses SearchService which implements Search interface

### LoginController
- **Purpose:** User authentication
- **Key Methods:**
  - `handleLogin()` - Validates credentials, opens dashboard
- **Integration:** Uses AccountAddToSQL for validation

### SignupController
- **Purpose:** User registration
- **Key Methods:**
  - `handleSignup()` - Creates new account
- **Integration:** Uses AccountAddToSQL to insert account

### DashboardController
- **Purpose:** Admin panel
- **Key Methods:**
  - `loadUsers()` - Displays all users in table
  - `handleLogout()` - Closes dashboard
- **Integration:** Uses AccountAddToSQL to fetch users

---

## Common Tasks

### Adding a New Room
**File:** `MainController.java`

1. Add button handler:
```java
@FXML
public void handleRoom4Booking() {
    openBookingWindow("New Room Name", 4, 150.00);
}
```

2. Update `main.fxml` with new room card

3. Update `SearchService.java` to include new room:
```java
availableRooms.add(new Room("NewUU Hotel", 4, RoomStyle.DELUXE, 150, RoomStatus.AVAILABLE));
```

---

### Adding Room Service Charges
**File:** `BookingController.java`

In `handleProceedToPayment()` method:
```java
// Add room service charge
InvoiceItem roomService = new InvoiceItem("Room Service", 25.00);
invoice.addItem(roomService);

// Add housekeeping charge
InvoiceItem housekeeping = new InvoiceItem("Housekeeping", 15.00);
invoice.addItem(housekeeping);
```

---

### Changing Payment Methods
**File:** `PaymentController.java`

Add new payment type:
```java
@FXML
private RadioButton checkRadio;

// In handlePayment()
else if (checkRadio.isSelected()) {
    String checkNumber = checkNumberField.getText();
    String bankName = bankNameField.getText();
    transaction = new CheckTransaction(totalPrice, checkNumber, bankName);
}
```

---

### Customizing Search Filters
**File:** `SearchService.java`

Add price filter:
```java
public boolean searchRoom(RoomStyle roomStyle, LocalDate startDate, int durationInDays, double maxPrice) {
    for (Room room : availableRooms) {
        if (room.getStyle() == roomStyle && 
            room.getRoomStatus() == RoomStatus.AVAILABLE &&
            room.getBookingPrice() <= maxPrice) {
            matchingRooms.add(room);
        }
    }
}
```

---

## Testing Checklist

### ✅ Authentication Flow
- [ ] Create new account
- [ ] Login with valid credentials
- [ ] Login with invalid credentials (should fail)
- [ ] View users in dashboard

### ✅ Booking Flow
- [ ] Click "Book Now" on each room
- [ ] Enter guest name
- [ ] Select dates (check-out after check-in)
- [ ] Verify total calculation
- [ ] Proceed to payment

### ✅ Payment Flow
- [ ] Cash payment with sufficient amount
- [ ] Cash payment with insufficient amount (should fail)
- [ ] Credit card payment with valid details
- [ ] Credit card payment with missing details (should fail)
- [ ] Verify booking saved after successful payment

### ✅ Search Flow
- [ ] Search for STANDARD rooms
- [ ] Search for DELUXE rooms
- [ ] Search for SUITE rooms
- [ ] Search with different durations
- [ ] Verify results display correctly

---

## Troubleshooting

### Issue: FXML not found
**Solution:** Check that FXML files are in `src/main/resources/fxml/`

### Issue: Controller not found
**Solution:** Verify `module-info.java` exports controller package:
```java
exports org.example.controller;
opens org.example.controller to javafx.fxml;
```

### Issue: Database connection failed
**Solution:** Check `DatabaseConnection.java` credentials and ensure MySQL is running

### Issue: Images not loading
**Solution:** Verify images are in `src/main/resources/` and paths start with `/`

### Issue: Payment always fails
**Solution:** Check transaction logic:
- Cash: cashTendered >= amount
- Credit Card: cardName and zipCode not null

---

## Code Style Guidelines

### Controller Methods
```java
@FXML
public void handleButtonClick() {
    // 1. Get input from UI
    String input = textField.getText();
    
    // 2. Validate input
    if (input.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "Please enter value");
        return;
    }
    
    // 3. Call business logic
    boolean success = service.doSomething(input);
    
    // 4. Update UI or show result
    if (success) {
        showAlert(Alert.AlertType.INFORMATION, "Success!");
    }
}
```

### Opening New Windows
```java
private void openNewWindow() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/window.fxml"));
        Parent root = loader.load();
        
        // Pass data to controller if needed
        WindowController controller = loader.getController();
        controller.setData(data);
        
        Stage stage = new Stage();
        stage.setTitle("Window Title");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
    }
}
```

### Alert Messages
```java
private void showAlert(Alert.AlertType type, String message) {
    Alert alert = new Alert(type);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
```

---

## Database Schema Reference

```sql
-- Users table (Rasulbek's work)
CREATE TABLE users (
    USER_ID INT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR(50) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL
);

-- Rooms table (Asadbek's work)
CREATE TABLE rooms (
    room_id INT PRIMARY KEY,
    room_name VARCHAR(100),
    price DOUBLE,
    status VARCHAR(20)  -- 'AVAILABLE' or 'BOOKED'
);

-- Bookings table (G'ayratjon's work)
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

---

## Quick Commands

### Build Project
```bash
mvn clean compile
```

### Run Application
```bash
mvn javafx:run
```

### Package JAR
```bash
mvn package
```

### Run Tests (if added)
```bash
mvn test
```

---

## Contact & Credits

**Integration Work:** Complete MVC refactoring, payment integration, search implementation

**Original Team:**
- Rasulbek - Registration & Admin
- G'ayratjon - Booking System
- Mahmud - Payment Processing
- Asadbek - Room Management
- Sarvarbek - Search Interface

**Technologies:** Java 17, JavaFX 17.0.2, MySQL 8.0, Maven
