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
@RequestMapping("binance")
public class GetDataBinance {
    private List<Binance> getData() throws Exception {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        
        WebDriver driver = new FirefoxDriver(options);
        
        driver.get("https://www.binance.com/en/nft/ranking?tab=collection");
        
        Thread.sleep(3000);
        
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
        
        Elements elements = document.select("div.css-vurnku");

        List<Binance> binances = new ArrayList<>();
        for (Element element : elements) {
            Binance binance = new Binance();
            binance.setImg(element.select("img").attr("src"));
            binance.setName(element.select("div.css-31460s").text());
            Elements priceElements = element.select("div.css-9w1gf");
            boolean isExists = priceElements.size() == 2;
            binance.setVolume(isExists ? priceElements.get(0).text() : "");
            binance.setFloorPrice(isExists ? priceElements.get(1).text() : "");
            if(isExists) binances.add(binance);
        }
        return binances;
    }
    
    @GetMapping("")
    public ResponseEntity<?> getDataFromBinance() throws Exception {
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(getData()));
    }
}
