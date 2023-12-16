package oop.backend.utils.scroll.top;

import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NiftyGatewayTopUtil {
    public static Document scrollAndGet( String request) throws Exception {
        String url = "https://www.niftygateway.com/rankings";
        WebDriver driver = ScrollUtil.setUp(url);
        if (!request.equals("Day")) {
            String xpathExpression = String.format("//div[text()='%s']", request);
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
            Thread.sleep(1000);
            count++;
        }
        driver.quit();
        return masterDocument;
    }
}
