package oop.backend.utils.sort;

import oop.backend.utils.ScrollUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NiftyGatewaySortUtil {
    public static String scrollAndGet(String url, String request) throws Exception {
        WebDriver driver = ScrollUtil.setUp(url);
        
        if (!request.equals("Day")) {
            String xpathExpression = String.format("//div[@class='css-tzs92q' and text()='%s']", request);
            WebElement sortButton = driver.findElement(By.xpath(xpathExpression));
            sortButton.click();
        }
        
        Thread.sleep(1000);
        Document masterDocument = null;
        int count = 0;
        
        while (true) {
            WebElement nextButton = driver.findElement(By.cssSelector("button.css-jh7xkz[title='Next Page']"));
            
            if (nextButton.equals(null))
                break;
            if (count == 10)
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
