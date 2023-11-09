import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitterTextParser {
    public static void main(String[] args) {
        // Ví dụ bài tweet
        String tweet = "This is a sample tweet with #hashtag and @username mentioned. #ignore";

        // Loại bỏ hashtag và tên người dùng
        String cleanedTweet = removeHashtagsAndUserMentions(tweet);

        // In ra nội dung đã làm sạch
        System.out.println(cleanedTweet);
    }

    public static String removeHashtagsAndUserMentions(String tweet) {
        // Loại bỏ hashtag
        String withoutHashtags = tweet.replaceAll("#\\w+", "");

        // Loại bỏ tên người dùng
        String withoutUserMentions = withoutHashtags.replaceAll("@\\w+", "");

        // Loại bỏ khoảng trắng kéo dài
        String cleanedTweet = withoutUserMentions.trim();

        return cleanedTweet;
    }
}
