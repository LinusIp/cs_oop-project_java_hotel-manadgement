# RoomCard Component - Visual Guide

## Component Structure

```
┌─────────────────────────────────────────┐
│         RoomCard Component              │
│         (280px width)                   │
│                                         │
│  ┌───────────────────────────────────┐ │
│  │                                   │ │
│  │     Image or Description Text     │ │
│  │         (250 x 150 px)            │ │
│  │      Rounded corners (10px)       │ │
│  │                                   │ │
│  └───────────────────────────────────┘ │
│                                         │
│  Room Name (Bold, 14px)                 │
│                                         │
│  ┌───────────────────────────────────┐ │
│  │ $120/night        [Book Now]      │ │
│  │ (Green)           (Button)        │ │
│  └───────────────────────────────────┘ │
│                                         │
└─────────────────────────────────────────┘
  White background, rounded (15px)
  Drop shadow effect
```

---

## Code to UI Mapping

### Input:
```java
RoomCard.createRoomCard(120, "Deluxe Room", "Beautiful sea view")
```

### Output:
```
┌─────────────────────────────────┐
│                                 │
│    Beautiful sea view           │  ← description
│      (250 x 150 px)             │
│                                 │
├─────────────────────────────────┤
│  Deluxe Room                    │  ← name (bold)
├─────────────────────────────────┤
│  $120/night    [Book Now]       │  ← price + button
└─────────────────────────────────┘
```

---

## Usage Flow

```
Developer writes:
┌──────────────────────────────────────────┐
│ VBox card = RoomCard.createRoomCard(    │
│     120,                                 │
│     "Deluxe Room",                       │
│     "Beautiful sea view"                 │
│ );                                       │
└──────────────────────────────────────────┘
                  ↓
RoomCard.java processes:
┌──────────────────────────────────────────┐
│ 1. Create VBox container                 │
│ 2. Add image placeholder                 │
│ 3. Add room name label                   │
│ 4. Add price label                       │
│ 5. Add book button                       │
│ 6. Apply styling                         │
│ 7. Return complete VBox                  │
└──────────────────────────────────────────┘
                  ↓
Developer gets:
┌──────────────────────────────────────────┐
│         Complete Room Card               │
│    Ready to add to any layout            │
└──────────────────────────────────────────┘
```

---

## Multiple Cards Layout

```java
HBox container = new HBox(20);
container.getChildren().addAll(
    RoomCard.createRoomCard(120, "Suite", "Luxury"),
    RoomCard.createRoomCard(95, "Deluxe", "Modern"),
    RoomCard.createRoomCard(45, "Standard", "Budget")
);
```

### Result:
```
┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│             │  │             │  │             │
│   Luxury    │  │   Modern    │  │   Budget    │
│             │  │             │  │             │
├─────────────┤  ├─────────────┤  ├─────────────┤
│   Suite     │  │   Deluxe    │  │  Standard   │
├─────────────┤  ├─────────────┤  ├─────────────┤
│ $120 [Book] │  │ $95  [Book] │  │ $45  [Book] │
└─────────────┘  └─────────────┘  └─────────────┘
    20px gap        20px gap
```

---

## Styling Details

### Card Container:
```
Background: White (#FFFFFF)
Border Radius: 15px
Padding: 15px
Width: 280px
Shadow: Drop shadow (rgba(0,0,0,0.1))
Spacing: 10px between children
```

### Image Placeholder:
```
Size: 250 x 150 px
Background: Light gray (#f0f0f0)
Border Radius: 10px
Content: Description text or image
```

### Room Name:
```
Font: Bold
Size: 14px
Color: Black (default)
```

### Price Label:
```
Font: Bold
Color: Green (#4ECCA3)
Text: "$[price]/night"
```

### Book Button:
```
Background: Transparent
Border: Gray (#ddd)
Border Radius: 5px
Text: "Book Now"
```

---

## Component Hierarchy

```
VBox (card)
│
├── StackPane (imgPlaceholder)
│   │
│   └── Label (description) OR ImageView (image)
│
├── Label (roomName)
│
└── HBox (footer)
    │
    ├── Label (roomPrice)
    │
    ├── Region (spacer - grows to fill space)
    │
    └── Button (bookBtn)
```

---

## Before vs After

### BEFORE (Main.java):
```java
private VBox createRoomCard(String name, String price, 
                           String imagePath, int roomId, 
                           double nightlyPrice) {
    VBox card = new VBox(10);
    card.setPadding(new Insets(15));
    card.setStyle("-fx-background-color: white; ...");
    
    StackPane imgPlaceholder = new StackPane();
    // ... 40 more lines ...
    
    return card;
}

// Usage:
createRoomCard("Suite", "$120/night", "/room1.png", 1, 120.00)
```

### AFTER (RoomCard.java):
```java
public static VBox createRoomCard(int price, 
                                  String name, 
                                  String description) {
    // Same 45 lines of UI code
    // But now reusable!
}

// Usage:
RoomCard.createRoomCard(120, "Suite", "Luxury")
```

---

## Integration Example

### In Your JavaFX Application:
```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.UI.RoomCard;

public class MyApp extends Application {
    @Override
    public void start(Stage stage) {
        HBox root = new HBox(20);
        
        // Add 3 room cards
        root.getChildren().addAll(
            RoomCard.createRoomCard(120, "Suite", "Luxury"),
            RoomCard.createRoomCard(95, "Deluxe", "Modern"),
            RoomCard.createRoomCard(45, "Standard", "Budget")
        );
        
        Scene scene = new Scene(root, 900, 300);
        stage.setScene(scene);
        stage.show();
    }
}
```

### Visual Result:
```
┌────────────────────────────────────────────────────────────┐
│                     MyApp Window                           │
├────────────────────────────────────────────────────────────┤
│                                                            │
│  ┌──────────┐    ┌──────────┐    ┌──────────┐           │
│  │          │    │          │    │          │           │
│  │  Luxury  │    │  Modern  │    │  Budget  │           │
│  │          │    │          │    │          │           │
│  ├──────────┤    ├──────────┤    ├──────────┤           │
│  │  Suite   │    │  Deluxe  │    │ Standard │           │
│  ├──────────┤    ├──────────┤    ├──────────┤           │
│  │$120[Book]│    │$95 [Book]│    │$45 [Book]│           │
│  └──────────┘    └──────────┘    └──────────┘           │
│                                                            │
└────────────────────────────────────────────────────────────┘
```

---

## Summary

### One Line Creates This:
```java
RoomCard.createRoomCard(120, "Deluxe Room", "Sea view")
```

### Produces:
```
┌─────────────────────────────────┐
│                                 │
│         Sea view                │
│       (250 x 150)               │
│                                 │
├─────────────────────────────────┤
│  Deluxe Room (Bold)             │
├─────────────────────────────────┤
│  $120/night    [Book Now]       │
└─────────────────────────────────┘
  White card with shadow
  280px wide, rounded corners
```

**Simple. Reusable. Consistent.** ✅
