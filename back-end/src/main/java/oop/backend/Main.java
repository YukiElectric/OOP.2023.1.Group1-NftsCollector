package oop.backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


import java.io.IOException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception{
//        ConfigurationBuilder builder = new ConfigurationBuilder();

        // Đặt các thuộc tính xác thực
//        builder.setOAuthConsumerKey("HZCwXOprs6dY3LZnvWCnIteAX");
//        builder.setOAuthConsumerSecret("yDf424QYutPoSABaKE3zCKWEB1gDHML7E1QN98K0W9XwEvRtp4");
//        builder.setOAuthAccessToken("975163018253225984-fOvWvBgYo9T2R1nRtmVki8F98jqMrVS");
//        builder.setOAuthAccessTokenSecret("mjS0d2EYKDbwTRufDqgZUwzyBbHEP4jk4pbmMsLNa1g0m");

        // Tạo đối tượng Twitter
//        Twitter twitter = new TwitterFactory(builder.build()).getInstance();
//        final TwitterV2 v2 = TwitterV2ExKt.getV2(twitter);
//        final TweetsResponse tweets = v2.getTweets(
//                new long[]{1519966129946791936L},
//                V2DefaultFields.mediaFields, null, null, "attachments", null, "attachments.media_keys");
//        System.out.println("tweets = " + tweets);
//
//
//        //--------------------------------------------------
//        // getUsers example
//        //--------------------------------------------------
//        final long twitterDesignId = 87532773L;
//        final UsersResponse users = v2.getUsers(new long[]{twitterDesignId}, null, null, "");
//        System.out.println("users = " + users);

        // Thực hiện một yêu cầu API
//        Query query = new Query("#java");
//        QueryResult result = twitter.search(query);

//         Duyệt qua kết quả
//        for (Status status : result.getTweets()) {
//            // In dữ liệu ra console
//            System.out.println("Text: " + status.getText());
//        }


//        String url = "https://www.youtube.com";
//
//        try {
//            Document document = Jsoup.connect(url).get();
//
//            System.out.println(document);
//
//            Elements links = document.select("[data-testid=tweetTex]");
//
//            for (Element link : links) {
//                System.out.println(link);
//                System.out.println("Tag Name: " + link.tagName());
//                System.out.println("Text: " + link.text());
//                System.out.println("Attributes: " + link.attributes());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String apiUrl = "https://api.twitter.com/1.1/search/tweets.json?q=nasa&result_type=popular"; // Thay thế yourhashtag bằng hashtag cần tìm

        // Thêm các thông tin xác thực API vào yêu cầu HTTP GET
        HttpGet request = new HttpGet(apiUrl);
        request.addHeader("Authorization", "AAAAAAAAAAAAAAAAAAAAAM4fqgEAAAAArWcr5xCkqkGihM1Dx48z%2F3fKyGY%3DS7IJFoA5qPF79caLYLVetEOGo5x1fFcM89zz5s0WqQ2KzzxHBA"); // Thay thế YOUR_TWITTER_BEARER_TOKEN bằng bearer token của bạn

        try {
            // Sử dụng Apache HttpClient để gửi yêu cầu
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(request);

            // Kiểm tra mã trạng thái HTTP
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Đọc dữ liệu từ phản hồi và hiển thị nó
                String jsonResponse = EntityUtils.toString(response.getEntity());
                System.out.println(jsonResponse);
            } else {
                System.out.println("Lỗi: Mã trạng thái HTTP " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
