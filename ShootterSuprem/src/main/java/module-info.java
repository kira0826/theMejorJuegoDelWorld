module com.example.shoottersuprem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.shoottersuprem to javafx.fxml;
    exports com.example.shoottersuprem;
}