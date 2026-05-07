# Integration Architecture Diagram

## System Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                         USER INTERFACE                          │
│                         (FXML Files)                            │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                        CONTROLLERS                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐         │
│  │    Main      │  │    Login     │  │   Signup     │         │
│  │ Controller   │  │  Controller  │  │  Controller  │         │
│  └──────────────┘  └──────────────┘  └──────────────┘         │
│                                                                  │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐         │
│  │   Booking    │  │   Payment    │  │    Search    │         │
│  │ Controller   │  │  Controller  │  │  Controller  │         │
│  └──────────────┘  └──────────────┘  └──────────────┘         │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      SERVICE LAYER                              │
│  ┌──────────────┐                                               │
│  │    Search    │  (Implements Search Interface)                │
│  │   Service    │                                               │
│  └──────────────┘                                               │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      DOMAIN MODELS                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐         │
│  │    Guest     │  │     Room     │  │    Hotel     │         │
│  └──────────────┘  └──────────────┘  └──────────────┘         │
│                                                                  │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐         │
│  │   Invoice    │  │ InvoiceItem  │  │   Account    │         │
│  └──────────────┘  └──────────────┘  └──────────────┘         │
│                                                                  │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐         │
│  │     Cash     │  │ CreditCard   │  │     Bill     │         │
│  │ Transaction  │  │ Transaction  │  │ Transaction  │         │
│  └──────────────┘  └──────────────┘  └──────────────┘         │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      DATA ACCESS                                │
│  ┌──────────────┐  ┌──────────────┐                            │
│  │   Booking    │  │   Account    │                            │
│  │   Database   │  │  AddToSQL    │                            │
│  └──────────────┘  └──────────────┘                            │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      MySQL DATABASE                             │
│         (users, rooms, bookings tables)                         │
└─────────────────────────────────────────────────────────────────┘
```

---

## Booking Flow with Payment Integration

```
┌──────────┐
│  User    │
│  clicks  │
│ "Book"   │
└────┬─────┘
     │
     ▼
┌─────────────────────┐
│ BookingController   │
│                     │
│ 1. Get room details │
│ 2. Get guest name   │
│ 3. Get dates        │
│ 4. Calculate total  │
└────┬────────────────┘
     │
     │ Creates
     ▼
┌─────────────────────┐
│   Guest object      │
└─────────────────────┘
     │
     │ Creates
     ▼
┌─────────────────────┐
│   Invoice object    │
│                     │
│ + InvoiceItem       │
│   (Room charge)     │
└────┬────────────────┘
     │
     │ Passes to
     ▼
┌─────────────────────┐
│ PaymentController   │
│                     │
│ 1. Show invoice     │
│ 2. Select method    │
│ 3. Enter details    │
└────┬────────────────┘
     │
     │ Creates
     ▼
┌─────────────────────┐
│  BillTransaction    │
│  (Cash or Card)     │
└────┬────────────────┘
     │
     │ Calls
     ▼
┌─────────────────────┐
│ initiateTransaction │
│                     │
│ Returns: boolean    │
└────┬────────────────┘
     │
     ├─── TRUE ───┐
     │            │
     │            ▼
     │    ┌─────────────────────┐
     │    │ BookingDatabase     │
     │    │ .addBooking()       │
     │    │                     │
     │    │ - Save to DB        │
     │    │ - Update room status│
     │    └─────────────────────┘
     │            │
     │            ▼
     │    ┌─────────────────────┐
     │    │ Success Message     │
     │    │ "Booking confirmed" │
     │    └─────────────────────┘
     │
     └─── FALSE ──┐
                  │
                  ▼
          ┌─────────────────────┐
          │ Error Message       │
          │ "Payment failed"    │
          └─────────────────────┘
```

---

## Search System Integration

```
┌──────────┐
│  User    │
│  enters  │
│  search  │
│  criteria│
└────┬─────┘
     │
     ▼
┌─────────────────────┐
│ SearchController    │
│                     │
│ 1. Get room style   │
│ 2. Get start date   │
│ 3. Get duration     │
└────┬────────────────┘
     │
     │ Calls
     ▼
┌─────────────────────┐
│  SearchService      │
│  (implements        │
│   Search interface) │
└────┬────────────────┘
     │
     │ Calls
     ▼
┌─────────────────────┐
│ searchRoom()        │
│                     │
│ - Filter by style   │
│ - Check availability│
│ - Return matches    │
└────┬────────────────┘
     │
     │ Returns
     ▼
┌─────────────────────┐
│  List<Room>         │
└────┬────────────────┘
     │
     │ Display in
     ▼
┌─────────────────────┐
│  ListView           │
│  (in UI)            │
└─────────────────────┘
```

---

## MVC Pattern Implementation

```
┌─────────────────────────────────────────────────────────────┐
│                          VIEW                               │
│                      (FXML Files)                           │
│                                                             │
│  main.fxml    login.fxml    booking.fxml    payment.fxml   │
│  signup.fxml  search.fxml   dashboard.fxml                 │
│                                                             │
│  - Define UI layout                                         │
│  - No business logic                                        │
│  - Bind to controllers                                      │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ fx:controller
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       CONTROLLER                            │
│                    (Controller Classes)                     │
│                                                             │
│  MainController    LoginController    BookingController    │
│  SignupController  PaymentController  SearchController     │
│                                                             │
│  - Handle user events                                       │
│  - Validate input                                           │
│  - Call model methods                                       │
│  - Update view                                              │
└─────────────────────────────────────────────────────────────┘
                              │
                              │ Uses
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                         MODEL                               │
│                   (Domain Classes)                          │
│                                                             │
│  Guest    Room    Hotel    Invoice    Account              │
│  CashTransaction    CreditCardTransaction                   │
│  BookingDatabase    AccountAddToSQL                         │
│                                                             │
│  - Business logic                                           │
│  - Data structures                                          │
│  - Database operations                                      │
└─────────────────────────────────────────────────────────────┘
```

---

## Component Integration Map

```
┌─────────────────────────────────────────────────────────────┐
│                    TEAM MEMBER WORK                         │
└─────────────────────────────────────────────────────────────┘

Rasulbek's Work:              Integration:
┌──────────────────┐          ┌──────────────────┐
│ Account.java     │◄─────────│ LoginController  │
│ AccountAddToSQL  │          │ SignupController │
└──────────────────┘          │ DashboardCtrl    │
                              └──────────────────┘

G'ayratjon's Work:            Integration:
┌──────────────────┐          ┌──────────────────┐
│ BookingDatabase  │◄─────────│ BookingCtrl      │
│ RoomBooking      │          │ PaymentCtrl      │
└──────────────────┘          └──────────────────┘

Mahmud's Work:                Integration:
┌──────────────────┐          ┌──────────────────┐
│ BillTransaction  │◄─────────│ PaymentCtrl      │
│ CashTransaction  │          │ (processes       │
│ CreditCardTrans  │          │  payment before  │
│ Invoice          │          │  booking)        │
└──────────────────┘          └──────────────────┘

Asadbek's Work:               Integration:
┌──────────────────┐          ┌──────────────────┐
│ Room.java        │◄─────────│ MainController   │
│ RoomKey          │          │ SearchService    │
│ RoomCharge       │          │ BookingCtrl      │
└──────────────────┘          └──────────────────┘

Sarvarbek's Work:             Integration:
┌──────────────────┐          ┌──────────────────┐
│ Search interface │◄─────────│ SearchService    │
│ (empty)          │          │ SearchController │
└──────────────────┘          └──────────────────┘
```

---

## Data Flow Example: Complete Booking

```
Step 1: User Action
   User clicks "Book Now" on AI Smart Suite ($120/night)
   
Step 2: BookingController
   ├─ roomName = "AI Smart Suite"
   ├─ roomId = 1
   ├─ nightlyPrice = 120.00
   └─ Opens booking.fxml

Step 3: User Enters Details
   ├─ guestName = "John Doe"
   ├─ checkIn = 2026-05-05
   ├─ checkOut = 2026-05-07
   └─ nights = 2, totalPrice = $240

Step 4: Create Objects
   ├─ guest = new Guest()
   ├─ invoice = new Invoice()
   └─ invoice.addItem(new InvoiceItem("Room Booking", 240.00))

Step 5: PaymentController
   ├─ Display invoice: $240
   ├─ User selects: Cash Payment
   ├─ User enters: $300
   └─ transaction = new CashTransaction(240, 300)

Step 6: Process Payment
   ├─ success = transaction.initiateTransaction()
   └─ if (success) → Continue

Step 7: Save Booking
   ├─ BookingDatabase.addBooking(...)
   ├─ INSERT INTO bookings (...)
   ├─ UPDATE rooms SET status='BOOKED'
   └─ Return bookingId = 1

Step 8: Confirmation
   └─ Show alert: "Booking confirmed! Booking ID: 1"
```

---

## Key Integration Points

### 1. Payment → Booking
```java
// PaymentController.java
boolean paymentSuccess = transaction.initiateTransaction();
if (paymentSuccess) {
    BookingDatabase.addBooking(...);  // Only if payment succeeds
}
```

### 2. Invoice → Payment
```java
// BookingController.java
Invoice invoice = new Invoice();
invoice.addItem(new InvoiceItem("Room Booking", totalPrice));
// Pass invoice to PaymentController
```

### 3. Search → Results
```java
// SearchController.java
boolean found = searchService.searchRoom(roomStyle, startDate, duration);
List<Room> rooms = searchService.getAvailableRooms();
// Display in ListView
```

### 4. FXML → Controller
```xml
<!-- booking.fxml -->
<GridPane fx:controller="org.example.controller.BookingController">
    <Button fx:id="proceedToPaymentButton" onAction="#handleProceedToPayment"/>
</GridPane>
```

---

## Summary

This integration connects:
- ✅ 5 team members' work
- ✅ 7 controllers
- ✅ 7 FXML views
- ✅ 1 service layer
- ✅ 15+ domain classes
- ✅ 3 database tables

All without modifying the original business logic!
