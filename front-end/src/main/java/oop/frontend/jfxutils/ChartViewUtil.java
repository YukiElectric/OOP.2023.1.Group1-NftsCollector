package oop.frontend.jfxutils;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.common.TaskRequest;
import oop.frontend.controller.LoadingController;

import java.util.List;
import java.util.Map;

public class ChartViewUtil {
    public static <T> void setViewLineChart(VBox vBox, String apiUrl, String apiRequest, Class<T> controllerClass) {
        TaskRequest taskRequest = new TaskRequest(apiUrl, apiRequest);
        taskRequest.setOnSucceeded(event -> {
            List<Map<String, String>> data = taskRequest.getValue();
            vBox.getChildren().clear();
            try {
                T itemController = controllerClass.getDeclaredConstructor(List.class).newInstance(data);
                vBox.getChildren().add((Node) itemController);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                Thread.currentThread().interrupt();
            });
        });

        taskRequest.setOnFailed(event -> {
            taskRequest.getException().printStackTrace();
            vBox.getChildren().clear();
            try {
                vBox.getChildren().add(WebViewUtil.setView(Constants.ERROR_VIEW));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        vBox.getChildren().clear();
        try {
            vBox.getChildren().add(new LoadingController());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new Thread(taskRequest).start();
    }
}
