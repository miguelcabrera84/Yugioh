module com.example.yugioh {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.yugioh to javafx.fxml;
    exports com.example.yugioh;
}