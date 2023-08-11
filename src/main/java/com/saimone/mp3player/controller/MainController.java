package com.saimone.mp3player.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, resetButton, previousButton, nextButton;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private Slider volumeSlider;

    private Media media;
    private MediaPlayer mediaPlayer;

    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private int songNumber;
    private final int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRunning;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs = new ArrayList<>();
        directory = new File("src/main/resources/com/saimone/mp3player/music");
        files = directory.listFiles();

        if(files != null && files.length != 0) {
            Collections.addAll(songs, files);
        }

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());
    }

    public void playMedia() {
        mediaPlayer.play();
    }

    public void pauseMedia() {
        mediaPlayer.pause();
    }

    public void resetMedia() {
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void previousMedia() {
        songNumber--;

        if (songNumber < 0) {
            songNumber = songs.size() - 1;
        }

        mediaPlayer.stop();

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());
        playMedia();
    }

    public void nextMedia() {
        songNumber++;

        if (songNumber >= songs.size()) {
            songNumber = 0;
        }

        mediaPlayer.stop();

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());
        playMedia();
    }

    public void changeSpeed(ActionEvent event) {

    }

    public void beginTimer() {

    }

    public void cancelTimer() {

    }
}

