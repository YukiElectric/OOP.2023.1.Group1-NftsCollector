package oop.backend.utils.sort.trending;

import oop.backend.utils.ScrollUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BinanceTrendingUtil {
    public static String scrollAndGet( String request) throws Exception {
        String url = "https://www.binance.com/en/nft/ranking?tab=trend&chain=ALL";
        WebDriver driver = ScrollUtil.setUp(url);
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

        String html = ScrollUtil.scrollHTML(driver);
        Document pageDocument = Jsoup.parse(html);

        driver.quit();
        return String.valueOf(pageDocument);
    }
}
