package oop.frontend.jfxutils;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import oop.frontend.common.TaskRequest;
import oop.frontend.controller.LoadingController;

import java.util.List;
import java.util.Map;

public class VboxViewUtil {
    public static <T> void setViewVBox(VBox vBox, String apiUrl, String apiRequest, Class<T> controllerClass){
        TaskRequest taskRequest = new TaskRequest(apiUrl,apiRequest);
        taskRequest.setOnSucceeded(event -> {
            List<Map<String,String>> data = taskRequest.getValue();
            vBox.getChildren().clear();
            for(Map<String, String> item : data) {
                try {
                    T itemController = controllerClass.getDeclaredConstructor(Map.class).newInstance(item);
                    vBox.getChildren().add((Node) itemController);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            Platform.runLater(() ->{
                Thread.currentThread().interrupt();
            });
        });
        taskRequest.setOnFailed(event -> {
            taskRequest.getException().printStackTrace();
            vBox.getChildren().clear();
            try {
                vBox.getChildren().add(WebViewUtil.setView("/html/Error.txt"));
//                vBox.getChildren().add(new ImageView(new Image(App.class.getResource(Constants.APP_ERROR).openStream())));

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
