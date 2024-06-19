module com.example.midterm200540293 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.midterm200540293 to javafx.fxml;
    exports com.example.midterm200540293;
}