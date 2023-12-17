package oop.backend.utils.scroll.trending;

import oop.backend.config.Url;
import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class RaribleTrendingUtil {
    public static Document scrollAndGet(String request) throws Exception{
        String url = Url.URL_RARIBLE_TRENDING + request;
        WebDriver driver = ScrollUtil.setUp(url);
        Document document = ScrollUtil.scrollInfinitePage(driver);
        return document;
    }
}
