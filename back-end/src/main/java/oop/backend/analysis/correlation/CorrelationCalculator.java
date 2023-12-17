package oop.backend.analysis.correlation;

import oop.backend.analysis.Analyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;

import java.util.*;

import static java.lang.Math.abs;


/** Tính toán hệ số tương quan theo 2 cách Pearson và Spearman. Có thể thấy được độ tương quan dữ liệu qua hệ số
    tương quan nằm trong khoảng -1 đến 1.
 **/
@Component
@RestController
@RequestMapping("${api.v1}/analysis")
public class CorrelationCalculator extends Analyzer{

    /* List<DataElement> loadData() ... */

    @Override
    public List<?> handleData() throws Exception {
        List<MarketplaceStatistics> corList = new ArrayList<>();
        TopGetter topGetter = new TopGetter();
        List<DataElement> topData = topGetter.handleData();


        Map<String, List<DataElement>> groupedMarketplace = topData.stream().collect(Collectors.groupingBy(DataElement::getMarketplace));
        groupedMarketplace.forEach((marketplace, dataElements) -> {
            System.out.println(marketplace);
            List<DataElement> dataset = new ArrayList<>();
            dataElements.forEach(dataElement -> {
                dataset.add(dataElement);
            });
            double spearman = calSpearman(dataset);
            double pearson = calPearson(dataset);
            corList.add(new MarketplaceStatistics(marketplace, spearman, pearson));
        });
        return corList;
    }

    @GetMapping("/correlation")
    public ResponseEntity<?> response() {
        try {
            return ResponseEntity.ok(handleData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }

    // Pearson correlation coefficient:  r = ((∑ XY)-(∑X)(∑Y)) / sqrt [n(∑X^2)-(∑X)^2)*(n(∑Y^2)-(∑Y)^2]
    private double calPearson(List<DataElement> corList) {
        double coefPearson = 0.0;
        double sumX = 0.0, sumY = 0.0, sumXY = 0.0, sumX2 = 0.0, sumY2 = 0.0;
        int n = corList.size();
        for (DataElement o : corList) {
            double x = o.getVolume();
            double y = o.getContentList().size();
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
            sumY2 += y * y;
        }
        double numerator = n * sumXY - sumX * sumY;     // Tính tử số
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY)); // Tính mẫu số
        if (denominator != 0) {
            coefPearson = numerator / denominator;
        }
        return coefPearson;
    }


    // Spearman correlation coefficient: r = 1 - (6*∑di^2)/ (n*(n^2-1))
    private double calSpearman(List<DataElement> corList){
        double coefSpearman = 0.0;
        int n = corList.size();
        double[] arrNFT = new double[corList.size()];
        double[] arrTweet = new double[corList.size()];


        int i = 0;
        for (DataElement o : corList){
            arrNFT[i] = o.getVolume();
            arrTweet[i] = o.getContentList().size();
            i++;
        }

        double[] rankNFT = getRanks(arrNFT);
        double[] rankTweet = getRanks(arrTweet);
        // di = |rank Xi - rank Yi|
        // Tính tổng các di^2
        double sum = 0;
        for (int j=0; j<n ;j++){
            double tmp = abs(rankTweet[j] - rankNFT[j]);
            sum +=tmp*tmp;
        }

        // Công thức cuối cùng
        coefSpearman = 1 - (6*sum) / (n*(n*n-1));
        return coefSpearman;
    }

    // Lấy rank để tính Spearman
    private static double[] getRanks(double[] orgArray) {
        // Tạo một bản copy của orgArray và sort nó
        double[] sortedArray = Arrays.copyOf(orgArray, orgArray.length);
        Arrays.sort(sortedArray);

        // Gán rank
        double[] ranks = new double[orgArray.length];
        for (int i = 0; i < orgArray.length; i++) {

            int index =0;
            for (int j=0; j<sortedArray.length; j++){
                if (orgArray[i] == sortedArray[j])
                {
                    ranks[i] = j+1;
                    index=j;
                    break;
                }
            }

            int tiedRank = 0;
            // Đếm các giá trị trùng (các rank trùng nhau) thì set tất cả bằng rank trung bình
            for (int j = index+1; j < sortedArray.length; j++){
                if (sortedArray[j] == sortedArray[index]) tiedRank++;
            }

            ranks[i] = (2*ranks[i]+tiedRank)/ 2;
        }

        return ranks;
    }


}
