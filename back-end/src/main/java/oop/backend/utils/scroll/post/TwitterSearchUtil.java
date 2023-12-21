package oop.backend.utils.scroll.post;

import oop.backend.config.Account;
import oop.backend.config.Url;
import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class TwitterSearchUtil {
    public static Document getByTag(String request) throws Exception {
        String navigate = "https://twitter.com/search?q=" + request + "&src=typed_query&f=live";
        WebDriver driver = TwitterLoginUtil.loginAndNavigate(Url.URL_TWITTER_LOGIN, navigate, Account.USERNAME, Account.PASSWORD);
        Document document = ScrollUtil.scrollInfinitePage(driver);
        return document;
    }
}
