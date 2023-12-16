package oop.backend.utils.scroll.top;

import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class RaribleTopUtil {
    public static Document scrollAndGet(String request) throws Exception{
        String url = "https://rarible.com/explore/ethereum/collections" + request;
        WebDriver driver = ScrollUtil.setUp(url);
        Document document = ScrollUtil.scrollInfinitePage(driver);
        return document;
    }
}
