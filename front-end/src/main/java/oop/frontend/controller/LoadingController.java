package oop.frontend.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import oop.frontend.App;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController extends VBox implements Initializable {

    @FXML
    private ProgressBar progressBar;
    public LoadingController() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/loading.fxml"));
        fxmlLoader.setController(this);
        this.getChildren().add(fxmlLoader.load());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
