package oop.backend.utils.scroll.top;

import oop.backend.config.Url;
import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

public class OpenSeaTopUtil {
    public static Document scrollAndGet(String request) throws Exception {
        String url = Url.URL_OPENSEA_TOP + request;
        WebDriver driver = ScrollUtil.setUp(url);
        Document masterDocument = null;
        masterDocument = ScrollUtil.scrollAndGetDoc(driver);
        return masterDocument;
    }
}
