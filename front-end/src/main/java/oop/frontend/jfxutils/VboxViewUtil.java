package oop.frontend.jfxutils;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import oop.frontend.common.TaskRequest;
import oop.frontend.controller.HeaderController;
import oop.frontend.controller.LoadingController;

import java.util.*;

public class VboxViewUtil {
    public static <T> void setViewVBox(VBox vBox, String apiUrl, String apiRequest, Class<T> controllerClass, int header){
        TaskRequest taskRequest = new TaskRequest(apiUrl,apiRequest);
        taskRequest.setOnSucceeded(event -> {
            List<Map<String,String>> data = taskRequest.getValue();
            vBox.getChildren().clear();
            int index = 1;
            for(Map<String, String> item : data) {
                try {
                    Map<String, String> newItem = new LinkedHashMap<>();
                    newItem.put("No", String.valueOf(index++));
                    newItem.putAll(item);
                    T itemController = controllerClass.getDeclaredConstructor(Map.class).newInstance(newItem);
                    if(header*index==2) {
                        Set<String> keys = newItem.keySet();
                        vBox.getChildren().add(new HeaderController(keys));
                    }
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
                for (int i = 0; i < 2; i++) {
                    vBox.getChildren().add(new Label(""));
                }
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
