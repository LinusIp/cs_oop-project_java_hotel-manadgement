# Mock Mode - Testing Without Database

The application is now running in **MOCK MODE** which means it works without MySQL database.

## What's Different?

### Mock Services Created:
1. **MockAccountService.java** - Replaces AccountAddToSQL
   - Stores accounts in memory (HashMap)
   - Pre-loaded test accounts:
     - Username: `admin` / Password: `admin123`
     - Username: `test` / Password: `test123`

2. **MockBookingDatabase.java** - Replaces BookingDatabase
   - Stores bookings in memory (HashMap)
   - Generates booking IDs automatically
   - Prints booking info to console

## How to Use

### 1. Login
- Use pre-existing accounts:
  - `admin` / `admin123`
  - `test` / `test123`
- Or create a new account (stored in memory only)

### 2. Book a Room
- Click "Book Now" on any room
- Enter guest name and dates
- Proceed to payment

### 3. Payment
- **Cash Payment**: Enter amount >= room price
- **Credit Card**: Enter any name and zip code
- Payment validation works normally

### 4. View Dashboard
- Login first
- Click "Users" to see all accounts (in memory)

## Features That Work:
✅ User signup/login
✅ Room booking flow
✅ Payment processing (Cash & Credit Card)
✅ Invoice creation
✅ Guest object creation
✅ Booking confirmation
✅ Admin dashboard
✅ View users

## Limitations:
❌ Data is NOT saved between runs
❌ No persistent storage
❌ Bookings reset when app closes

## Console Output

The mock services print to console:
```
Mock: Account created - john
Mock: Login attempt - admin - SUCCESS
Mock: Booking created - ID: 1, Guest: John Doe, Room: AI Smart Suite, Total: $240.0
```

## Switching to Real Database

When you're ready to use MySQL:

1. Start MySQL server
2. Create database and tables (see README.md)
3. Update DatabaseConnection.java password
4. Replace Mock services with real ones:
   - Change `MockAccountService` back to `AccountAddToSQL`
   - Change `MockBookingDatabase` back to `BookingDatabase`

## Testing Scenarios

### Test 1: Complete Booking
1. Open app
2. Click "Book Now" on AI Smart Suite
3. Enter: Guest = "John Doe", Check-in = Today, Check-out = Tomorrow
4. Click "Proceed to Payment"
5. Select "Cash Payment", Enter: $150
6. Click "Complete Payment"
7. ✅ Should show: "Payment successful! Booking ID: 1"

### Test 2: Failed Payment
1. Book a room (total = $120)
2. Select "Cash Payment", Enter: $50
3. Click "Complete Payment"
4. ✅ Should show: "Payment failed"

### Test 3: Credit Card Payment
1. Book a room
2. Select "Credit Card Payment"
3. Enter: Name = "John Smith", Zip = "12345"
4. Click "Complete Payment"
5. ✅ Should show: "Payment successful!"

### Test 4: Login & Dashboard
1. Click "Sign In"
2. Enter: Username = "admin", Password = "admin123"
3. Click "Sign In"
4. ✅ Dashboard opens
5. Click "Users"
6. ✅ Table shows admin and test accounts

## Integration Still Works!

Even in mock mode, all integrations are functional:
- ✅ Payment system validates before booking
- ✅ Invoice created with InvoiceItem
- ✅ Guest objects instantiated
- ✅ CashTransaction / CreditCardTransaction used
- ✅ All UI flows work correctly

The only difference is data storage (memory vs database).
