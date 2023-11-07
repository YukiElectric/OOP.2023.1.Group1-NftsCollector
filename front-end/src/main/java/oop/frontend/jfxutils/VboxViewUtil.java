package oop.frontend.jfxutils;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import oop.frontend.controller.PostItemController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VboxViewUtil {
    public static void setViewViewVBox(VBox vBox, String apiUrl, String apiRequest){
        TaskUtil taskUtil = new TaskUtil(apiUrl,apiRequest);
        taskUtil.setOnSucceeded(event -> {
            List<Map<String,String>> data = taskUtil.getValue();
            vBox.getChildren().clear();
            for(Map<String, String> item : data) {
                try {
                    vBox.getChildren().add(new PostItemController(item));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Platform.runLater(() ->{
                Thread.currentThread().interrupt();
            });
        });
        taskUtil.setOnFailed(event -> {
            taskUtil.getException().printStackTrace();
        });

        ProgressIndicator progressIndicator = new ProgressIndicator(-1);
        vBox.getChildren().clear();
        vBox.getChildren().add(progressIndicator);
        new Thread(taskUtil).start();
    }
}
