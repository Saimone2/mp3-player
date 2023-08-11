module com.saimone.mp3player {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.saimone.mp3player.controller to javafx.fxml;
    exports com.saimone.mp3player;
}