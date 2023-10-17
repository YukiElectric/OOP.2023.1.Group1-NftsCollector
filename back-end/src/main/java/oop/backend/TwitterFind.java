package oop.backend;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class TwitterFind {
    public static void main(String[] args) throws Exception{
//        String username = "yukiElectric72";
//        String password = "07022003";
//
//        String loginUrl = "https://twitter.com/i/flow/login";
//
//        Connection.Response loginResponse = Jsoup.connect(loginUrl)
//                .data("username", username)
//                .data("password", password)
//                .method(Connection.Method.POST)
//                .execute();
//
//        Map<String, String> cookies = loginResponse.cookies();
//
//        String dataUrl = "https://twitter.com/home";
//
//        Document document =  Jsoup.connect(dataUrl).cookies(cookies).get();
//        System.out.println(document);

        String apiKey = "HYRQdiYclmUvhjLm9L4gwM8eM";
        String apiSecret = "PWLTBfq1W4AuYLbMwBPKO2g8Q6BW1Sr3gGbgSSJycmM9cQhHfc";
        String accessToken = "975163018253225984-C2AHZjBJmWkLIoe1aG9AiTNxpN14S9y";
        String accessTokenSecret = "K1JAA2vNkuNazgaWGysOfTQhccVUTKE5TVQi0UTt4AAlT";

        try {
            // Xây dựng URL cho yêu cầu Twitter API
            String apiUrl = "https://api.twitter.com/1.1/search/tweets.json?q=nasa&result_type=popular";
            URL url = new URL(apiUrl);

            // Tạo kết nối HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Thêm thông tin xác thực vào tiêu đề yêu cầu
            String authHeader = "Bearer " + apiKey + ":" + apiSecret;
            connection.setRequestProperty("Authorization", authHeader);

            // Gửi yêu cầu và nhận phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc dữ liệu từ phản hồi
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Hiển thị dữ liệu phản hồi từ Twitter API
                System.out.println(response.toString());
            } else {
                // Xử lý lỗi
                System.out.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
