package oop.backend;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("rarible")
public class GetDataRarible {
    private List<Rarible> getData() throws Exception {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        
        WebDriver driver = new FirefoxDriver(options);
        
        driver.get("https://rarible.com/explore/ethereum/collections?period=MONTH");
        
        Thread.sleep(1000);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
            + ", document.body.offsetHeight, document.documentElement.clientHeight,"
            + " document.documentElement.scrollHeight,"
            + " document.documentElement.offsetHeight )");
        int steps = 10;
        long delayBetweenStepsInMillis = 1000;
        long scrollStep = pageHeight / steps;
        for (int i = 0; i < steps; i++) {
            long yOffset = i * scrollStep;
            jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
            Thread.sleep(delayBetweenStepsInMillis);
        }
        
        String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
        
        driver.quit();
        
        Document document = Jsoup.parse(html);
        
        Elements elements = document.select("div.sc-icLIcW");
        
        List<Rarible> raribles = new ArrayList<>();
        
        for (Element element : elements) {
            Rarible rarible = new Rarible();
            rarible.setImage(element.select("div. sc-hyBYnz").select("img").attr("src"));
            rarible.setCollection(element.select("div.sc-dmLtQE").select("span").text());
            Elements priceElements = element.select("div.sc-ktEKTO");
            boolean isExists = priceElements.size() == 2;
            rarible.setVolume(isExists ? priceElements.get(0).text() : "");
            rarible.setVolume(isExists ? priceElements.get(1).text() : "");
            raribles.add(rarible);
        }
        return raribles;
    }
    
    @GetMapping("")
    public ResponseEntity<?> getDataFromRarible() throws Exception{
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(getData()));
    }
}
