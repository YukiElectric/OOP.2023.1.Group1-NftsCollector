package oop.backend;

import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("opensea")
public class GetDataOpenSea {
    private List<OpenSea> getData() throws Exception{
//        String proxyAdress = "127.0.0.1:51822";
//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy(proxyAdress);
//        proxy.setSslProxy(proxyAdress);
        
        FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("--headless");
//        options.setCapability("proxy",proxy);
        WebDriver driver = new FirefoxDriver();

        driver.get("https://opensea.io/collection/michelin3xplorerclub");

        Thread.sleep(5000);

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
            + ", document.body.offsetHeight, document.documentElement.clientHeight,"
            + " document.documentElement.scrollHeight,"
            + " document.documentElement.offsetHeight )");
        int steps = 20;
        long delayBetweenStepsInMillis = 1000;
        long scrollStep = pageHeight / steps;
        for (int i = 0; i < 50; i++) {
            if(i==10) {
//                delayBetweenStepsInMillis = 20000;
                break;
            }
            else delayBetweenStepsInMillis = 1000;
            long yOffset = i * scrollStep;
            jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
            Thread.sleep(delayBetweenStepsInMillis);
        }

        String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");

//        driver.quit();

        Document document = Jsoup.parse(html);
//      System.out.println(document);

        Elements elements = document.select("div.sc-66a9f2fe-1");
//      System.out.println(elements.size());

        List<OpenSea> openSeas = new ArrayList<>();
        for(Element element : elements){
            OpenSea openSea = new OpenSea();
            openSea.setImage(element.select("img").attr("src"));
            openSea.setName(element.select("div.sc-ae5b1802-6").text());
            openSea.setRarityRank(element.select("div.sc-7c87496-1").text());
            openSea.setPrice(element.select("div.sc-2d751d28-0").text());

            if(!openSea.getName().isEmpty()) openSeas.add(openSea);
        }

        return openSeas;
    }
    
    @GetMapping("")
    public ResponseEntity<?> getImage() throws Exception{
        Gson gson = new Gson();
        List<OpenSea> countData = getData();
        System.out.println(countData.size());
        return ResponseEntity.ok(gson.toJson(countData));
    }
}
