package oop.backend.analyzer;

import oop.backend.dtos.BaseDTO;

import java.util.*;

import static java.lang.Math.abs;


/** Lớp này sẽ phân tích sự tương quan theo nhiều cách:
    - makeCorrelationData() nhận vào một đối tượng NFT (chứa thông tin trên các sàn nft) và một đối tượng TwitterAnalyzer
    và trả về một danh sách các DataCorrelation. Có thể duyệt bảng này để hiển thị trực quan bằng biểu đồ.
    - tính toán hệ số tương quan theo 2 cách Pearson và Spearman. Có thể thấy được độ tương quan dữ liệu qua hệ số
    tương quan nằm trong khoảng -1 đến 1.
 **/
public class CorrelationViewer {
    public List<DataCorrelation> makeCorrelationData(List<BaseDTO> baseData, Hashtable<String, Integer> twitterData){

        List<DataCorrelation> corList = new ArrayList<>();
        for (BaseDTO o : baseData){
            String hashtag = o.getCollection();
            if (twitterData.containsKey(hashtag))               // Gán dữ liệu về giá nft và số lượng hashtag của 1 loại nft vào cùng 1 đối tượng
                corList.add(                                    // Tạo và thêm các đối tượng tương quan vào list
                        new DataCorrelation(
                                hashtag,
                                Integer.parseInt(o.getVolume()),
                                twitterData.get(hashtag)));
        }
        return corList;
    }

    // Pearson correlation coefficient:  r = ((∑ XY)-(∑X)(∑Y)) / sqrt [n(∑X^2)-(∑X)^2)*(n(∑Y^2)-(∑Y)^2]
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
        double numerator = n * sumXY - sumX * sumY;     // Tính tử số
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY)); // Tính mẫu số
        if (denominator != 0) {
            coefPearson = numerator / denominator;
        }
        return coefPearson;
    }


    // Spearman correlation coefficient: r = 1 - (6*∑di^2)/ (n*(n^2-1))
    public double calSpearman(List<DataCorrelation> corList){
        double coefSpearman = 0.0;
        int n = corList.size();
        double[] arrNFT = new double[corList.size()];
        double[] arrTweet = new double[corList.size()];


        int i = 0;
        for (DataCorrelation o : corList){
            arrNFT[i] = o.getNftData();
            arrTweet[i] = o.getTweetData();
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
