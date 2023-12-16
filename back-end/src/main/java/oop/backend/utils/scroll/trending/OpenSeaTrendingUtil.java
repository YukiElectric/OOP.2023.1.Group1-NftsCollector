package oop.backend.utils.scroll.trending;

import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class OpenSeaTrendingUtil {
    public static Document scrollAndGet(String request) throws Exception {
        String url = "https://opensea.io/rankings/trending" + request;
        WebDriver driver = ScrollUtil.setUp(url);
        Document masterDocument = null;
        masterDocument = ScrollUtil.scrollAndGetDoc(driver);
        return masterDocument;
    }
}
