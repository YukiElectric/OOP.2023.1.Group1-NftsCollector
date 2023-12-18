package oop.backend.utils.scroll.top;

import oop.backend.config.Url;
import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BinanceTopUtil {
    public static String scrollAndGet( String request) throws Exception {
        WebDriver driver = ScrollUtil.setUp(Url.URL_BINANCE_TOP);
        WebElement skipButton = driver.findElement(By.xpath("//button[@data-bn-type='button' and contains(@class, 'css-1s94759')]"));
        skipButton.click();
        if(!request.equals("24H")) {
            String xpathExpression =
                String.format("//div[@class='css-11cvlnv' and text()='%s']", request);
            WebElement sortButton = driver.findElement(
                By.xpath(xpathExpression));
            sortButton.click();
        }
        Thread.sleep(500);
        Document masterDocument = null;
        int count = 0;
        
        while (true) {
            Thread.sleep(1000);

            WebElement nextButton = driver.findElement(By.xpath("//*[@id='next-page']"));
            if (nextButton.equals(null))
                break;
            if (count == 5)
                break;

            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
            Document pageDocument = Jsoup.parse(html);
            if (masterDocument == null) {
                masterDocument = pageDocument.clone();
            } else {
                Elements bodyElements = pageDocument.body().children();
                for(Element element : bodyElements){
                    masterDocument.body().appendChild(element);
                }
            }

            nextButton.click();
            count++;
        }
        
        driver.quit();
        return String.valueOf(masterDocument);
    }
}
