package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.enums.RoomStyle;
import org.example.service.SearchService;
import org.example.hotel.Room;

import java.time.LocalDate;
import java.util.List;

public class SearchController {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField durationField;

    @FXML
    private ComboBox<String> roomStyleCombo;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> resultsListView;

    private SearchService searchService;

    @FXML
    public void initialize() {
        searchService = new SearchService();
        
        startDatePicker.setValue(LocalDate.now());
        
        ObservableList<String> roomStyles = FXCollections.observableArrayList(
            "STANDARD", "DELUXE", "FAMILYSUITE", "BUSSINESSSUITE"
        );
        roomStyleCombo.setItems(roomStyles);
        roomStyleCombo.setValue("STANDARD");
        
        searchButton.setOnAction(event -> handleSearch());
    }

    @FXML
    public void handleSearch() {
        LocalDate startDate = startDatePicker.getValue();
        String durationText = durationField.getText().trim();
        String roomStyleText = roomStyleCombo.getValue();

        if (startDate == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a start date.");
            return;
        }

        int duration = 1;
        if (!durationText.isEmpty()) {
            try {
                duration = Integer.parseInt(durationText);
                if (duration < 1) {
                    showAlert(Alert.AlertType.WARNING, "Duration must be at least 1 day.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Please enter a valid duration.");
                return;
            }
        }

        RoomStyle roomStyle = RoomStyle.valueOf(roomStyleText);
        
        boolean found = searchService.searchRoom(roomStyle, startDate, duration);
        
        if (found) {
            List<Room> availableRooms = searchService.getAvailableRooms();
            ObservableList<String> results = FXCollections.observableArrayList();
            
            for (Room room : availableRooms) {
                results.add("Room " + room.getRoomNumber() + " - " + room.getRoomStyle() + 
                           " - $" + room.getBookingPrice() + "/night");
            }
            
            resultsListView.setItems(results);
        } else {
            resultsListView.setItems(FXCollections.observableArrayList("No rooms available for selected criteria."));
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
