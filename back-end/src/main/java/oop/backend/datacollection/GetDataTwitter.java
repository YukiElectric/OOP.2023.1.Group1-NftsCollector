package oop.backend.datacollection;

import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.GetAttrTwitter;
import oop.backend.dtos.TwitterDTO;
import oop.backend.utils.JsonHandlerUtil;
import oop.backend.utils.TwitterLoginUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
@Component
@RestController
@RequestMapping("${api.v1}/twitter")
public class GetDataTwitter implements DataCrawler{
    @Value("${twitter.login}")
    private String twitterLink;
    private String username = "Fuon141204";
    private String password ="phuongct1412";
    private final AttrGetter<TwitterDTO> twitterAttr = new GetAttrTwitter();
    @Value("${PATH_TWITTER}")
    private String  PATH_TWITTER;
    public List<TwitterDTO> getData() throws Exception {
        WebDriver driver = TwitterLoginUtil.loginAndNavigate(twitterLink,username,password);
        
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
            + ", document.body.offsetHeight, document.documentElement.clientHeight,"
            + " document.documentElement.scrollHeight,"
            + " document.documentElement.offsetHeight )");
        int steps =1;
        long delayBetweenStepsInMillis = 1000;
        long scrollStep = pageHeight / steps;
        
        List<TwitterDTO> twitters = new ArrayList<>();
        
        for (int i = 0; i < 20; i++) {
            String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
            Document document = Jsoup.parse(html);
            Elements elements = document.select("div[data-testid='cellInnerDiv']");
            for (Element element : elements) {
                TwitterDTO twitter = twitterAttr.attrGet(element);
                if(twitter != null) twitters.add(twitter);
            }
            long yOffset = i * scrollStep;
            jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
            Thread.sleep(delayBetweenStepsInMillis);
        }
        driver.quit();
        return twitters;
    }
    private final JsonHandlerUtil<TwitterDTO> jsonHandler = new JsonHandlerUtil<>(PATH_TWITTER);
    @GetMapping("")
    public ResponseEntity<?> getDataFromTwitter() {
        return jsonHandler.handleJsonOperation(() -> getData());
    }
}

