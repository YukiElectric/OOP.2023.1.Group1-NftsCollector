package oop.backend.utils.sort.post;

import oop.backend.utils.ScrollUtil;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class TwitterSearchUtil {
    public static Document getByTag(String request) throws Exception {
        String twitterLink = "https://twitter.com/login";
        String username = "Fuon141204";
        String password = "phuongct1412";
        String navigate = "https://twitter.com/search?q=" + request + "&src=typed_query&f=live";
        WebDriver driver = TwitterLoginUtil.loginAndNavigate(twitterLink, navigate, username, password);
        Document document = ScrollUtil.scrollAndGetDoc(driver);
        return document;
    }
}
