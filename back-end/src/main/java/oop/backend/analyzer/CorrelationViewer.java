package oop.backend.analyzer;

import java.util.ArrayList;
import java.util.List;


// Lớp này sẽ tính toán hệ số tương quan

public class CorrelationViewer {
    public List<DataCorrelation> viewCorList(DataCorrelation o){
        List<DataCorrelation> corList = new ArrayList<>();

        //TODO: fix

        return corList;
    }

    public double calPearson(List<DataCorrelation> corList) {
        double coefPearson = 0.0;
        double sumX = 0.0, sumY = 0.0, sumXY = 0.0, sumX2 = 0.0, sumY2 = 0.0;
        int n = corList.size();
        for (DataCorrelation o : corList) {
            double x = o.getNftData();
            double y = o.getTweetData();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
            sumY2 += y * y;
        }
        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));
        if (denominator != 0) {
            coefPearson = numerator / denominator;
        }
        return coefPearson;
    }


        // TODO: coefSpearman

}
