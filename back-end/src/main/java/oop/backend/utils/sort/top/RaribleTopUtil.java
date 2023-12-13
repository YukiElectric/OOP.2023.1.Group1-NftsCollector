package oop.backend.utils.sort.top;

import oop.backend.utils.ScrollUtil;
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
