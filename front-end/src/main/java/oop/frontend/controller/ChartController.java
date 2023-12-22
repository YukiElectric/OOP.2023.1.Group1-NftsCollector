package oop.frontend.controller;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChartController extends VBox {
//    private final StackPane stackPane;

    public ChartController(List<Map<String, String>> dataList) {
//        stackPane = new StackPane();

        CategoryAxis xAxis = new CategoryAxis();
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis volumeYAxis = new NumberAxis();
        NumberAxis postYAxis = new NumberAxis();

//        xAxis.setLabel("Collection");
        volumeYAxis.setLabel("Volume");

        // Scatter
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

        this.getChildren().addAll(currencyLabel, scatterChart, lineChartPost, lineChartVolume);
    }
}
