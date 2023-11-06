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

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("niftygateway")
public class GetDataNiftyGateway {
    private List<NiftyGateway> getData() throws Exception {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        
        //        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        
        WebDriver driver = new FirefoxDriver(options);
        
        driver.get("https://www.niftygateway.com/marketplace");
        
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
        System.out.println(document);
        Elements elements = document.select("div.css-jj9f9r");
        
        List<NiftyGateway> niftyGateways = new ArrayList<>();
        
        for(Element element : elements){
            NiftyGateway niftyGateway = new NiftyGateway();
            niftyGateway.setImage(element.select("img").first().attr("src"));
            niftyGateway.setName(element.select("p.css-1q0cfzn").text());
            niftyGateway.setFloorPrice(element.select("p.css-81s8ad").text());
            if(niftyGateway.getFloorPrice().isBlank()) niftyGateway.setFloorPrice("Drop on live");
            niftyGateway.setAuthor(element.select("p.css-or9p4e").select("a[href^=/marketplace/artist/]").text());
            niftyGateway.setEdition(element.select("div.css-jtxebx").select("div.css-or9p4e").text());
            niftyGateways.add(niftyGateway);
        }
        return niftyGateways;
    }
    
    @GetMapping("")
    public ResponseEntity<?> getDataFromNiftyGateway() throws Exception{
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(getData()));
    }
}
