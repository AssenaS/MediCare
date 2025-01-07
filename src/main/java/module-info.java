module com.example.medicare_projekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.medicare_projekt to javafx.fxml;
    exports com.example.medicare_projekt;
}