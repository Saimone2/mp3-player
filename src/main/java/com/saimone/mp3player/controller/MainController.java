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
    private ArrayList<File> songs;
    private int songNumber;
    private final int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
    private Timer timer;
    private boolean isRunning;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs = new ArrayList<>();
        File directory = new File("src/main/resources/com/saimone/mp3player/music");
        File[] files = directory.listFiles();

        if(files != null && files.length != 0) {
            Collections.addAll(songs, files);
        }

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());

        for (int speed : speeds) {
            speedBox.getItems().add(speed + "%");
        }

        speedBox.setOnAction(this::changeSpeed);

        volumeSlider.valueProperty().addListener((observableValue, number, t1) -> mediaPlayer.setVolume(volumeSlider.getValue() * 0.01));

        songProgressBar.setStyle("-fx-accent: #00c853;");
    }

    public void playMedia() {
        beginTimer();
        changeSpeed(null);
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
    }

    public void pauseMedia() {
        if(timer != null) {
            cancelTimer();
            mediaPlayer.pause();
        }
    }

    public void resetMedia() {
        songProgressBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void previousMedia() {
        if(isRunning) {
            cancelTimer();
        }
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
        if(isRunning) {
            cancelTimer();
        }
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
        if(speedBox.getValue() == null) {
            mediaPlayer.setRate(1);
        } else {
            String stringVal = speedBox.getValue().substring(0, speedBox.getValue().length() - 1);
            mediaPlayer.setRate(Integer.parseInt(stringVal) * 0.01);
        }
    }

    public void beginTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                isRunning = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();

                songProgressBar.setProgress(current / end);

                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void cancelTimer() {
        isRunning = false;
        timer.cancel();
    }
}