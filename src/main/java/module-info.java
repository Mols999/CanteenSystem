module com.example.canteensystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.canteensystem to javafx.fxml;
    exports com.example.canteensystem;
}