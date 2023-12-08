package oop.backend.crawler.top;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.GetAttrOpenSea;
import oop.backend.dtos.OpenSeaDTO;
import oop.backend.utils.JsonHandlerUtil;
import oop.backend.utils.PathFixUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@Component
@RestController
@RequestMapping("${api.v1}/top")
public class GetDataOpenSea {
    private String PATH_OPEN_SEA= PathFixUtil.fix(App.class.getResource("/json/open_sea_data.json").getPath());
    private final AttrGetter<OpenSeaDTO> openSeaAttr = new GetAttrOpenSea();
    private List<OpenSeaDTO> getData() throws Exception {
        String url = "https://opensea.io/rankings";
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

        WebDriver driver = new FirefoxDriver(options);
        driver.get(url);
        Thread.sleep(3000);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
            + ", document.body.offsetHeight, document.documentElement.clientHeight,"
            + " document.documentElement.scrollHeight,"
            + " document.documentElement.offsetHeight )");
        int steps = 10;
        long delayBetweenStepsInMillis = 1000;
        long scrollStep = pageHeight / steps;
        
        List<OpenSeaDTO> openSeas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
            Document document = Jsoup.parse(html);
            Elements elements = document.select("div.sc-e7b51c31-0");
            for (Element element : elements) {
                OpenSeaDTO openSea = openSeaAttr.attrGet(element);
                openSeas.add(openSea);
            }
            long yOffset = i * scrollStep;
            jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
            Thread.sleep(delayBetweenStepsInMillis);
        }
        driver.quit();
        return openSeas;
    }
    
    private final JsonHandlerUtil<OpenSeaDTO> jsonHandler = new JsonHandlerUtil<>(PATH_OPEN_SEA);
    @GetMapping("/opensea/{selection}")
    public ResponseEntity<?> getDataFromOpenSea() {
        return jsonHandler.handleJsonOperation(() -> getData());
    }
}
