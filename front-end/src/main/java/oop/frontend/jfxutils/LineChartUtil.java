package oop.frontend.jfxutils;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.Random;

public class LineChartUtil {
    public static void createChart(LineChart<Number, Number> lineChart){
        int[] tweetData = new int[15];
        double[] priceData = new double[15];
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < 15; i++) {
            tweetData[i] = new Random().nextInt(100) +1 ;
            priceData[i] = new Random().nextDouble(100)+1;
            series.getData().add(new XYChart.Data<>(tweetData[i], priceData[i]));
        }
        lineChart.getData().add(series);
        lineChart.setTitle("Correlation Chart");
    }
}
