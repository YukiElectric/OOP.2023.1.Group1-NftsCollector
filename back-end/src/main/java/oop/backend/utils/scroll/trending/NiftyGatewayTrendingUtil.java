package oop.backend.utils.scroll.trending;

import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class NiftyGatewayTrendingUtil {
    public static Document scrollAndGet(String url) throws Exception{
        WebDriver driver = ScrollUtil.setUp(url);
        Document document = ScrollUtil.scrollInfinitePage(driver);
        return document;
    }
}
