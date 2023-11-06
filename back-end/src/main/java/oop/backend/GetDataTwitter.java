package oop.backend;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("twitter")
public class GetDataTwitter {
    private List<Twitter> getDataFromTwitter() throws Exception {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
        
        WebDriver driver = new FirefoxDriver();
        
        driver.get("https://twitter.com/login");  //Đưa url vào web để dẫn đến trang ở đây truyền trực tiếp nhưng thường hãy tách biến ra và config cứng lại
        
        Thread.sleep(1000); // Đợi 5s để web ổn định có thể lấy dễ hơn
        
        WebElement usernameInput = driver.findElement(  // Tìm đến phần tử có thẻ input và có 2 thuộc tính autocomplete và name
            By.cssSelector("input[autocomplete='username'][name='text']"));
        usernameInput.sendKeys("CTocdep165979"); //Set cho phần tử đó có giá trị là tài khoản đăng nhập
        usernameInput.sendKeys(Keys.ENTER);     //Ấn Enter để chuyển trang tùy vào trang có thể là ấn Enter có thể là button ấn nhưng 90% là enter
        
        Thread.sleep(1000);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Đợi 10s load web trong trường hợp web có js ẩn div hoặc các element khác
        WebElement password = wait.until(ExpectedConditions.presenceOfElementLocated( // Tìm phần tử có password như trên sau đó gửi mật khẩu của mình vào
            By.cssSelector("input[autocomplete='current-password'][name='password']")));
        password.sendKeys("07022003");
        password.sendKeys(Keys.ENTER);
        
        Thread.sleep(5000); // Đợi 5s để đăng nhập
        
        driver.navigate().to("https://twitter.com/search?q=%23nft&src=typed_query&f=live"); //Navigate hay chuyển hướng đến trang web cần sau khi đăng nhập do đã có cookie lên có thể lấy được dễ dàng
        
        Thread.sleep(5000); //Đợi 5s để navigate ổn định
        
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
            + ", document.body.offsetHeight, document.documentElement.clientHeight,"
            + " document.documentElement.scrollHeight,"
            + " document.documentElement.offsetHeight )");
        int steps =1;
        long delayBetweenStepsInMillis = 1000;
        long scrollStep = pageHeight / steps;
        
        List<Twitter> twitters = new ArrayList<>();
        
        for (int i = 0; i < 20; i++) {
            String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
            Document document = Jsoup.parse(html);
            Elements elements = document.select("div[data-testid='cellInnerDiv']");
            for (Element element : elements) {
                Twitter twitter = new Twitter();
                twitter.setImage(element.select("div[data-testid='tweetPhoto']").select("img").attr("src"));
                Elements tweetNames = element.select("div[data-testid='User-Name']").select("div.css-1hf3ou5");
                System.out.println(tweetNames.size());
                String name = "";
                for (Element tweetName : tweetNames) {
                    name += tweetName.text();
                }
                twitter.setUser(name);
                twitter.setHashtag(element.select("div.r-18u37iz").select("a[href^=/hashtag/]").text());
                twitter.setContent(element.select("div[data-testid='tweetText']").text());
                if (!twitter.getUser().isEmpty())
                    twitters.add(twitter);
            long yOffset = i * scrollStep;
            jsExecutor.executeScript("window.scrollTo(0, " + yOffset + ")");
            Thread.sleep(delayBetweenStepsInMillis);
            }
        }
        driver.quit();
        System.out.println(twitters.size());
        return twitters;
    }
    
    @GetMapping("")
    public ResponseEntity<?> getData() throws Exception {
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(getDataFromTwitter()));
    }
}

