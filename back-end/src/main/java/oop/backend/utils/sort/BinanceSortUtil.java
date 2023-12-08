package oop.backend.utils.sort;

import oop.backend.utils.ScrollUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BinanceSortUtil {
    public static String scrollAndGet(String url, String request) throws Exception {
        WebDriver driver = ScrollUtil.setUp(url);
        WebElement skipButton = driver.findElement(By.cssSelector("button.css-amo4x7"));
        skipButton.click();
        if(!request.equals("24H")) {
            String xpathExpression =
                String.format("//div[@class='css-11onxst' and text()='%s']", request);
            WebElement sortButton = driver.findElement(
                By.xpath(xpathExpression));
            sortButton.click();
        }
        Thread.sleep(1000);
        Document masterDocument = null;
        int count = 0;
        
        while (true) {
            WebElement nextButton = driver.findElement(By.xpath("//*[@id='next-page']"));
            
            if (nextButton.equals(null))
                break;
            if (count == 6)
                break;
            
            Thread.sleep(1000);
            
            String html = ScrollUtil.scrollHTML(driver);
            Document pageDocument = Jsoup.parse(html);
            if (masterDocument == null) {
                masterDocument = pageDocument.clone();
            } else {
                Elements bodyElements = pageDocument.body().children();
                masterDocument.body().children().addAll(bodyElements);
            }
            nextButton.click();
            count++;
        }
        
        driver.quit();
        return String.valueOf(masterDocument);
    }
}
