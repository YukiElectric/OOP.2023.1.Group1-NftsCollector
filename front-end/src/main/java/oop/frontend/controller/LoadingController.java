/**
 * Lớp LoadingController quản lý giao diện tải trong ứng dụng.
 */
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
import oop.frontend.common.Constants;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController extends VBox implements Initializable {

    @FXML
    private ProgressBar progressBar;

    /**
     * Constructor của LoadingController. Load giao diện từ file FXML và gắn controller.
     * @throws Exception
     */
    public LoadingController() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Constants.LOADING_VIEW_SOURCE));
        fxmlLoader.setController(this);
        this.getChildren().add(fxmlLoader.load());
    }

    /**
     * Phương thức khởi tạo giao diện tải và thực hiện animation cho ProgressBar.
     * @param location URL của controller.
     * @param resources ResourceBundle được sử dụng bởi controller.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Tạo Timeline để điều khiển animation của ProgressBar
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Thiết lập chạy vô hạn
        timeline.play(); // Bắt đầu chạy animation
    }
}
