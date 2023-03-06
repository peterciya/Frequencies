module com.example.frequencies {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;


    opens com.example.frequencies to javafx.fxml;
    exports com.example.frequencies;
}