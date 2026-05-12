# Hotel Management System - UML Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           PRESENTATION LAYER (UI)                            │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐
│      Main            │
│  (JavaFX App)        │
├──────────────────────┤
│ - accountService     │
│ - homeScene          │
│ - primaryStage       │
│ - roomContainer      │
├──────────────────────┤
│ + start()            │
│ + openLoginWindow()  │
│ + openSignupWindow() │
│ + openBookingWindow()│
│ + openPaymentWindow()│
│ + createDashboard()  │
│ + openCreateRoom()   │
│ + refreshRooms()     │
└──────────────────────┘
         │
         │ uses
         ▼
┌──────────────────────┐
│     RoomCard         │
│  (UI Component)      │
├──────────────────────┤
│ + createRoomCard()   │
│   (static)           │
└──────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                          BUSINESS LOGIC LAYER                                │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐       ┌──────────────────────┐
│   HotelManager       │       │   SearchService      │
├──────────────────────┤       ├──────────────────────┤
│ - hotel: Hotel       │       │ + searchRooms()      │
│ - rooms: List<Room>  │       │ + filterByPrice()    │
├──────────────────────┤       └──────────────────────┘
│ + getHotelInfo()     │
│ + getAllRooms()      │
│ + getRoomByNumber()  │
└──────────────────────┘

┌──────────────────────┐       ┌──────────────────────┐
│ HouseKeepingService  │       │  RoomKeyService      │
├──────────────────────┤       ├──────────────────────┤
│ + markRoomDirty()    │       │ + issueKey()         │
│ + markRoomClean()    │       │ + returnKey()        │
└──────────────────────┘       └──────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                            DOMAIN MODEL LAYER                                │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐       ┌──────────────────────┐
│       Hotel          │       │       Room           │
├──────────────────────┤       ├──────────────────────┤
│ - name: String       │◆─────▶│ - roomNumber: int    │
│ - location           │ 1   * │ - style: RoomStyle   │
│ - rooms: List<Room>  │       │ - price: double      │
├──────────────────────┤       │ - status: RoomStatus │
│ + addRoom()          │       ├──────────────────────┤
│ + getAllRooms()      │       │ + book()             │
└──────────────────────┘       │ + checkOut()         │
                               └──────────────────────┘

┌──────────────────────┐       ┌──────────────────────┐
│   HotelLocation      │       │      RoomKey         │
├──────────────────────┤       ├──────────────────────┤
│ - name: String       │       │ - keyId: String      │
│ - address: String    │       │ - room: Room         │
│ - rating: int        │       │ - isActive: boolean  │
└──────────────────────┘       └──────────────────────┘

┌──────────────────────┐       ┌──────────────────────┐
│    HouseKeeping      │       │       Guest          │
├──────────────────────┤       ├──────────────────────┤
│ - room: Room         │       │ - name: String       │
│ - isDirty: boolean   │       │ - email: String      │
│ - lastCleaned: Date  │       │ - phone: String      │
└──────────────────────┘       └──────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                          PAYMENT SYSTEM LAYER                                │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐
│      Invoice         │
├──────────────────────┤
│ - items: List        │◆────┐
│ - totalAmount        │     │
├──────────────────────┤     │ 1..*
│ + addItem()          │     │
│ + getTotalAmount()   │     ▼
└──────────────────────┘  ┌──────────────────────┐
                          │   InvoiceItem        │
                          ├──────────────────────┤
                          │ - description        │
                          │ - amount             │
                          └──────────────────────┘

         ┌──────────────────────┐
         │   BillTransaction    │
         │    <<abstract>>      │
         ├──────────────────────┤
         │ - amount: double     │
         ├──────────────────────┤
         │ + initiateTransaction()│
         │ + validatePayment()  │
         └──────────────────────┘
                   △
                   │
         ┌─────────┴─────────┐
         │                   │
┌────────────────┐  ┌────────────────────┐
│ CashTransaction│  │CreditCardTransaction│
├────────────────┤  ├────────────────────┤
│ - cashTendered │  │ - cardName         │
│ - change       │  │ - zipCode          │
├────────────────┤  ├────────────────────┤
│ + calculate()  │  │ + validate()       │
└────────────────┘  └────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                       REGISTRATION & ACCOUNT LAYER                           │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐       ┌──────────────────────┐
│      Account         │       │  AccountAddToSQL     │
├──────────────────────┤       ├──────────────────────┤
│ - ID: int            │       │ + getAllAccounts()   │
│ - username: String   │       │ + insertAccount()    │
│ - password: String   │       │ + deleteAccount()    │
│ - status: Status     │       │ + updateAccount()    │
├──────────────────────┤       │ + isValidLogin()     │
│ + resetPassword()    │       │ + getAccountByUser() │
└──────────────────────┘       └──────────────────────┘

         ┌──────────────────────┐
         │       Person         │
         │    <<abstract>>      │
         ├──────────────────────┤
         │ - name: String       │
         │ - address: Address   │
         │ - email: String      │
         │ - phone: String      │
         │ - account: Account   │
         └──────────────────────┘
                   △
                   │
         ┌─────────┴─────────┐
         │                   │
┌────────────────┐  ┌────────────────┐
│     Server     │  │     Guest      │
├────────────────┤  ├────────────────┤
│ + addCharge()  │  │ + checkIn()    │
└────────────────┘  │ + checkOut()   │
                    └────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                         DATA ACCESS LAYER (DAO)                              │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐
│ DatabaseConnection   │
├──────────────────────┤
│ - URL: String        │
│ - connection: Conn   │
├──────────────────────┤
│ + getConnection()    │
│ + initializeTables() │
└──────────────────────┘
         △
         │ uses
         │
┌────────┴─────────────┐
│                      │
┌──────────────────────┐  ┌──────────────────────┐
│  BookingDatabase     │  │ServiceChargeDatabase │
├──────────────────────┤  ├──────────────────────┤
│ + addBooking()       │  │ + getAllCharges()    │
│ + getAllRooms()      │  │ + addRoomCharge()    │
│ + addRoom()          │  │ + getTotalCharges()  │
│ + getAllBookings()   │  └──────────────────────┘
└──────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                            DATA TRANSFER OBJECTS                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐  ┌──────────────────────┐  ┌──────────────────────┐
│   RoomRecord         │  │   BookingRecord      │  │ RoomChargeRecord     │
├──────────────────────┤  ├──────────────────────┤  ├──────────────────────┤
│ - roomId             │  │ - bookingId          │  │ - chargeId           │
│ - roomName           │  │ - guestName          │  │ - description        │
│ - price              │  │ - roomId             │  │ - amount             │
│ - status             │  │ - checkIn            │  └──────────────────────┘
└──────────────────────┘  │ - checkOut           │
                          │ - totalPrice         │
                          └──────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                              ENUMERATIONS                                    │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐  ┌──────────────────────┐  ┌──────────────────────┐
│    RoomStyle         │  │    RoomStatus        │  │   AccountStatus      │
├──────────────────────┤  ├──────────────────────┤  ├──────────────────────┤
│ STANDARD             │  │ AVAILABLE            │  │ ACTIVE               │
│ DELUXE               │  │ BOOKED               │  │ CLOSED               │
│ SUITE                │  │ MAINTENANCE          │  │ SUSPENDED            │
└──────────────────────┘  └──────────────────────┘  └──────────────────────┘


┌─────────────────────────────────────────────────────────────────────────────┐
│                          KEY RELATIONSHIPS                                   │
└─────────────────────────────────────────────────────────────────────────────┘

Main ──uses──> RoomCard
Main ──uses──> BookingDatabase
Main ──uses──> AccountAddToSQL
Main ──uses──> HotelManager

BookingDatabase ──uses──> DatabaseConnection
AccountAddToSQL ──uses──> DatabaseConnection

HotelManager ──manages──> Hotel
Hotel ──contains──> Room (1 to many)
Hotel ──has──> HotelLocation

Invoice ──contains──> InvoiceItem (1 to many)
BillTransaction ──processes──> Invoice

Person ──has──> Account
Server ──extends──> Person
Guest ──extends──> Person

RoomKeyService ──issues──> RoomKey
HouseKeepingService ──manages──> HouseKeeping
```

## Architecture Summary

**Layered Architecture:**
1. **Presentation Layer** - JavaFX UI (Main, RoomCard)
2. **Business Logic Layer** - Services (HotelManager, SearchService, etc.)
3. **Domain Model Layer** - Core entities (Hotel, Room, Guest, etc.)
4. **Payment System** - Invoice and transaction processing
5. **Data Access Layer** - Database operations (BookingDatabase, etc.)
6. **DTOs** - Data transfer objects for database records

**Design Patterns Used:**
- **Singleton** - DatabaseConnection
- **Factory** - RoomCard.createRoomCard()
- **Strategy** - BillTransaction (Cash vs CreditCard)
- **DAO** - BookingDatabase, AccountAddToSQL
- **MVC** - Main (Controller), FXML (View), Domain classes (Model)
