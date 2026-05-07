# RoomCard Component - Usage Guide

## Overview
The `RoomCard` class is a reusable component that extracts the existing room card UI from `Main.java`. It allows any team member to easily create room cards without duplicating code.

---

## What It Does
✅ Reuses the **exact same UI design** from Main.java  
✅ No redesign - just extraction and parameterization  
✅ Simple static methods - no complex OOP  
✅ Beginner-friendly and easy to use  

---

## The Component

### File Location
```
src/main/java/org/example/UI/RoomCard.java
```

### Main Method (REQUIRED)
```java
public static VBox createRoomCard(int price, String name, String description)
```

**Parameters:**
- `price` - Room price per night (e.g., 120)
- `name` - Room name (e.g., "Deluxe Room")
- `description` - Room description (e.g., "Sea view")

**Returns:** `VBox` - Complete room card ready to add to any layout

---

## Usage Examples

### Example 1: Basic Usage
```java
import org.example.UI.RoomCard;
import javafx.scene.layout.VBox;

// Create a room card
VBox card = RoomCard.createRoomCard(120, "Deluxe Room", "Beautiful sea view");

// Add to your layout
root.getChildren().add(card);
```

### Example 2: Multiple Cards in HBox
```java
import org.example.UI.RoomCard;
import javafx.scene.layout.HBox;

HBox roomContainer = new HBox(20);
roomContainer.setAlignment(Pos.CENTER);

// Create 3 room cards
VBox card1 = RoomCard.createRoomCard(120, "AI Smart Suite", "High-tech amenities");
VBox card2 = RoomCard.createRoomCard(95, "Robotics Lab View", "Modern design");
VBox card3 = RoomCard.createRoomCard(45, "Freshman Dorm+", "Budget friendly");

// Add all cards
roomContainer.getChildren().addAll(card1, card2, card3);
```

### Example 3: With Custom Image
```java
// Create card with image
VBox card = RoomCard.createRoomCard(120, "Deluxe Room", "Sea view", "/room1.png");
```

### Example 4: With Button Action
```java
// Create card with custom action
VBox card = RoomCard.createRoomCard(120, "Deluxe Room", "Sea view", () -> {
    System.out.println("Book button clicked!");
    // Your booking logic here
});
```

---

## What the Card Looks Like

```
┌─────────────────────────────────┐
│                                 │
│     [Image or Description]      │
│         250x150 pixels          │
│                                 │
├─────────────────────────────────┤
│  Room Name (Bold)               │
├─────────────────────────────────┤
│  $120/night    [Book Now]       │
└─────────────────────────────────┘
```

**Styling (from original Main.java):**
- White background with rounded corners
- Drop shadow effect
- Green price text (#4ECCA3)
- Transparent "Book Now" button with border
- 280px width, 10px spacing

---

## Available Methods

### 1. Basic Card (No Image)
```java
VBox createRoomCard(int price, String name, String description)
```
Shows description text as placeholder

### 2. Card with Image
```java
VBox createRoomCard(int price, String name, String description, String imagePath)
```
Loads image from resources, falls back to description if image not found

### 3. Card with Action
```java
VBox createRoomCard(int price, String name, String description, Runnable onBookClick)
```
Executes custom code when "Book Now" is clicked

---

## Integration with Existing Code

### Before (Main.java):
```java
private VBox createRoomCard(String name, String price, String imagePath, int roomId, double nightlyPrice) {
    VBox card = new VBox(10);
    card.setPadding(new Insets(15));
    card.setStyle("-fx-background-color: white; -fx-background-radius: 15; ...");
    // ... 50 more lines ...
    return card;
}

// Usage
roomContainer.getChildren().addAll(
    createRoomCard("AI Smart Suite", "$120/night", "/room1.png", 1, 120.00),
    createRoomCard("Robotics Lab View", "$95/night", "/room2.png", 2, 95.00),
    createRoomCard("Freshman Dorm+", "$45/night", "/room3.png", 3, 45.00)
);
```

### After (Using RoomCard):
```java
import org.example.UI.RoomCard;

// Usage - Much simpler!
roomContainer.getChildren().addAll(
    RoomCard.createRoomCard(120, "AI Smart Suite", "High-tech amenities", "/room1.png"),
    RoomCard.createRoomCard(95, "Robotics Lab View", "Modern design", "/room2.png"),
    RoomCard.createRoomCard(45, "Freshman Dorm+", "Budget friendly", "/room3.png")
);
```

---

## Why This Design?

### ✅ Follows Requirements:
1. **Reuses existing UI** - Exact copy from Main.java, no redesign
2. **Simple and beginner-friendly** - Just static methods, no complex OOP
3. **No FXML/CSS** - Pure Java code
4. **Required method signature** - `createRoomCard(int price, String name, String description)`
5. **Easy for teammates** - One line to create a card

### ✅ Benefits:
- **DRY Principle** - Don't Repeat Yourself
- **Consistency** - All cards look the same
- **Maintainability** - Change UI in one place
- **Reusability** - Any developer can use it
- **Simplicity** - No learning curve

---

## Complete Working Example

```java
package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.UI.RoomCard;

public class RoomCardDemo extends Application {

    @Override
    public void start(Stage stage) {
        // Create container for cards
        HBox roomContainer = new HBox(20);
        roomContainer.setAlignment(Pos.CENTER);
        roomContainer.setPadding(new Insets(40));
        roomContainer.setStyle("-fx-background-color: white;");

        // Create 3 room cards using the component
        VBox card1 = RoomCard.createRoomCard(120, "AI Smart Suite", "High-tech amenities");
        VBox card2 = RoomCard.createRoomCard(95, "Robotics Lab View", "Modern design");
        VBox card3 = RoomCard.createRoomCard(45, "Freshman Dorm+", "Budget friendly");

        // Add cards to container
        roomContainer.getChildren().addAll(card1, card2, card3);

        // Create scene and show
        Scene scene = new Scene(roomContainer, 1000, 400);
        stage.setTitle("Room Card Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

---

## For Team Members

### If you need to display rooms:
```java
// Just call this method:
VBox card = RoomCard.createRoomCard(price, name, description);
container.getChildren().add(card);
```

### If you need custom behavior:
```java
// Add your own action:
VBox card = RoomCard.createRoomCard(120, "Deluxe Room", "Sea view", () -> {
    // Your code here
    openBookingWindow();
});
```

### If you need to modify the card:
```java
// Get the card and modify it:
VBox card = RoomCard.createRoomCard(120, "Deluxe Room", "Sea view");
card.setStyle(card.getStyle() + "-fx-border-color: red;"); // Add red border
```

---

## Technical Details

### Component Structure:
```
VBox (card)
├── StackPane (image placeholder)
│   └── Label (description) or ImageView (image)
├── Label (room name)
└── HBox (footer)
    ├── Label (price)
    ├── Region (spacer)
    └── Button (Book Now)
```

### Styling (Preserved from Main.java):
- Card: White background, 15px rounded corners, drop shadow
- Image: 250x150px, 10px rounded corners
- Name: Bold, 14px font
- Price: Green (#4ECCA3), bold
- Button: Transparent background, gray border, 5px rounded

---

## Summary

**What:** Reusable room card component  
**Where:** `org.example.UI.RoomCard`  
**How:** `RoomCard.createRoomCard(price, name, description)`  
**Why:** DRY, consistency, simplicity  

**Key Point:** This is the **exact same UI** from Main.java, just extracted into a reusable component. No redesign, no complexity, just simple extraction.

---

## Questions?

**Q: Can I change the styling?**  
A: Yes, but modify the RoomCard class so all cards stay consistent.

**Q: Can I add more fields?**  
A: Yes, add new parameters to the method or create a new overloaded method.

**Q: Does this work with FXML?**  
A: No, this is pure Java. But you can add the returned VBox to any FXML layout.

**Q: Is this the same design as Main.java?**  
A: Yes! Exact copy, just parameterized.
