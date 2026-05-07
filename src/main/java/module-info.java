module superproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.enums;
    opens org.example.enums to javafx.fxml;
    exports org.example.payment_system;
    opens org.example.payment_system to javafx.fxml;
    exports org.example.hotel;
    opens org.example.hotel to javafx.fxml;
    exports org.example.registration;
    opens org.example.registration to javafx.fxml;
    exports org.example.UI;
    opens org.example.UI to javafx.fxml;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml;
    exports org.example.service;
    opens org.example.service to javafx.fxml;
    exports org.example.demo;
    opens org.example.demo to javafx.fxml;
}