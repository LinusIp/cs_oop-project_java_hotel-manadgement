# RoomCard Component - Quick Start

## 1-Minute Guide

### Import
```java
import org.example.UI.RoomCard;
import javafx.scene.layout.VBox;
```

### Create a Card
```java
VBox card = RoomCard.createRoomCard(120, "Deluxe Room", "Beautiful sea view");
```

### Add to Layout
```java
container.getChildren().add(card);
```

---

## That's It! 🎉

### Full Example (Copy & Paste)
```java
import org.example.UI.RoomCard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// Create container
HBox roomContainer = new HBox(20);

// Create 3 cards
VBox card1 = RoomCard.createRoomCard(120, "AI Smart Suite", "High-tech");
VBox card2 = RoomCard.createRoomCard(95, "Robotics Lab", "Modern");
VBox card3 = RoomCard.createRoomCard(45, "Freshman Dorm", "Budget");

// Add to container
roomContainer.getChildren().addAll(card1, card2, card3);
```

---

## Method Signature (Required)
```java
public static VBox createRoomCard(int price, String name, String description)
```

**Parameters:**
- `price` → Room price (e.g., 120)
- `name` → Room name (e.g., "Deluxe Room")
- `description` → Room description (e.g., "Sea view")

**Returns:** `VBox` ready to use

---

## Optional: With Image
```java
VBox card = RoomCard.createRoomCard(120, "Deluxe Room", "Sea view", "/room1.png");
```

## Optional: With Action
```java
VBox card = RoomCard.createRoomCard(120, "Deluxe Room", "Sea view", () -> {
    System.out.println("Booked!");
});
```

---

## What You Get

✅ Exact same design as Main.java  
✅ White card with rounded corners  
✅ Drop shadow effect  
✅ Green price text  
✅ "Book Now" button  
✅ 280px width  

---

## Run the Demo
```bash
java org.example.demo.RoomCardDemo
```

---

## Need Help?
See `ROOMCARD_COMPONENT_GUIDE.md` for detailed documentation.
