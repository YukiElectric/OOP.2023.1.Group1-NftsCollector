package oop.backend.utils.sort.top;

import oop.backend.utils.ScrollUtil;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OpenSeaTopUtil {
    public static Document scrollAndGet(String request) throws Exception {
        String url = "https://opensea.io/rankings" + request;
        WebDriver driver = ScrollUtil.setUp(url);
        Thread.sleep(500);
        Document masterDocument = null;
        Thread.sleep(1000);
        masterDocument = ScrollUtil.scrollAndGetDoc(driver);
        return masterDocument;
    }
}
