module com.example.cardgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cardgame to javafx.fxml;
    exports com.example.cardgame;
}