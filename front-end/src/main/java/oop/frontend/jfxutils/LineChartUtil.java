package oop.frontend.jfxutils;

import javafx.application.Platform;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import oop.frontend.common.Constants;
import oop.frontend.common.TaskRequest;
import oop.frontend.controller.EPlatformController;
import oop.frontend.controller.LoadingController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class LineChartUtil {
    public static LineChartUtil getInstance() {
        return new LineChartUtil();
    }

    public void setView(VBox vbox, String apiUrl, String apiRequest) {
        TaskRequest taskRequest = new TaskRequest(apiUrl, apiRequest);
        taskRequest.setOnSucceeded(event -> {
            List<Map<String,String>> data = taskRequest.getValue();
            this.createChart(data, vbox);
            Platform.runLater(() ->{
                Thread.currentThread().interrupt();
            });
        });
        taskRequest.setOnFailed(event -> {
            taskRequest.getException().printStackTrace();
            vbox.getChildren().clear();
            vbox.setStyle("-fx-background-color: #9AE1EF; -fx-background-radius: 20;");
            try {
                vbox.getChildren().add(WebViewUtil.setView(Constants.ERROR_VIEW));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        vbox.getChildren().clear();
        vbox.setStyle("-fx-background-color: #9AE1EF; -fx-background-radius: 20;");
        try {
            vbox.getChildren().add(new LoadingController());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new Thread(taskRequest).start();
    }
    public void setViewChart(ToggleGroup toggleGroup, VBox openseaView, VBox binanceView, VBox niftyView, VBox raribleView, VBox positiveView) {
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();
                int selectedIndex = toggleGroup.getToggles().indexOf(selectedButton);
                switch (selectedIndex) {
                    /*
                    * Từ case 0->3 thay bằng / để lấy phân tích
                    * */
                    case 0 -> setView(openseaView,Constants.API_URL, "opensea");
                    case 1 -> setView(binanceView,Constants.API_URL, "binance");
                    case 2 -> setView(niftyView, Constants.API_URL, "nifty");
                    case 3 -> setView(raribleView, Constants.API_URL, "rarible");
                    case 4 -> VboxViewUtil.setViewVBox(positiveView,Constants.API_URL, "positive", EPlatformController.class, 1);
                }
            }
        });
    }

    private void createChart(List<Map<String, String>> dataList, VBox vbox){
        CategoryAxis xAxis = new CategoryAxis();
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis volumeYAxis = new NumberAxis();
        NumberAxis postYAxis = new NumberAxis();

        volumeYAxis.setLabel("Volume");
        NumberAxis volumeXAxis = new NumberAxis();
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(volumeXAxis, postYAxis);
        scatterChart.setTitle("Scatter chart Analysis");
        XYChart.Series<Number, Number> scatterSeries = new XYChart.Series<>();
        scatterSeries.setName("Volumes and Number Of Posts");


        LineChart<String, Number> lineChartVolume = new LineChart<>(xAxis, volumeYAxis);
        lineChartVolume.setTitle("Line chart Volume");
        XYChart.Series<String, Number> volumeSeries = new XYChart.Series<>();
        volumeSeries.setName("Volumes");


        LineChart<String, Number> lineChartPost = new LineChart<>(xAxis2, postYAxis);
        lineChartPost.setTitle("Line chart Number Of Posts");
        XYChart.Series<String, Number> postSeries = new XYChart.Series<>();
        postSeries.setName("Number Of Posts");

        Label currencyLabel = new Label();
        for (Map<String, String> data : dataList) {
            if(data.get("currency").matches("[a-zA-Z]+") ) {
                String currency = data.get("currency");
                currencyLabel = new Label("Currency: " + currency);
            }
            String collection = data.get("collection");
            String volume = data.get("volume");
            String post = data.get("numberOfPost");

            volumeSeries.getData().add(new XYChart.Data<>(collection, Double.parseDouble(volume)));
            postSeries.getData().add(new XYChart.Data<>(collection, Double.parseDouble(post)));
            scatterSeries.getData().add(new XYChart.Data<>(Double.parseDouble(volume), Double.parseDouble(post)));
        }

        lineChartVolume.getData().add(volumeSeries);
        lineChartPost.getData().add(postSeries);
        scatterChart.getData().add(scatterSeries);

        vbox.getChildren().clear();
        vbox.setStyle(null);
        vbox.getChildren().addAll(currencyLabel, scatterChart, lineChartPost, lineChartVolume);
    }
}
