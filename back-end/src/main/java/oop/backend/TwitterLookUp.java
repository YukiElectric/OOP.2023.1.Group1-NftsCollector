package oop.backend;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

public class TwitterLookUp {
    public static void main(String[] args) {
        String hashtag = "#nft";

        // Thay thế "YOUR_CONSUMER_KEY" và "YOUR_CONSUMER_SECRET" bằng thông tin ứng dụng của bạn
        Twitter twitter = new Twitter("YOUR_CONSUMER_KEY", "YOUR_CONSUMER_SECRET");

        try {
            // Gửi yêu cầu tìm kiếm tweet với hashtag
            List<winterwell.jtwitter.Status> tweets = twitter.search(hashtag);

            // In ra nội dung của các tweet
            for (winterwell.jtwitter.Status tweet : tweets) {
                System.out.println("@" + tweet.getUser().getScreenName() + ": " + tweet.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
