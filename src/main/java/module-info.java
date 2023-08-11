module com.saimone.mp3player {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.saimone.mp3player to javafx.fxml;
    exports com.saimone.mp3player;
}