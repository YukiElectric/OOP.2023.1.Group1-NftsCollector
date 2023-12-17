package oop.backend.utils.scroll.tag;

import oop.backend.crawler.hottag.HotTagCrawler;
import oop.backend.utils.scroll.ScrollUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HotTagUtil {
    public static Document getHTML(String settingUrl, String url) throws Exception {
        Document document = null;
        WebDriver driver = ScrollUtil.setUp(settingUrl);
        try {
            Thread.sleep(500);
            WebElement scrollButton = driver.findElement(By.cssSelector("input[name='infiniteScroll']"));
            scrollButton.click();
            WebElement saveButton = driver.findElement(By.cssSelector("button.pref-submit"));
            saveButton.click();
            Thread.sleep(5000);
            driver.navigate().to(url);
            Thread.sleep(500);
            try {
                WebElement refresh = driver.findElement(By.cssSelector("body"));
                Document refreshDocument = Jsoup.parse(refresh.getAttribute("outerHTML"));
                Element element = refreshDocument.select(":containsOwn(503 Service Temporarily Unavailable)").first();
                if (element != null) {
                    driver.navigate().refresh();
                    Thread.sleep(500);
                }
            } catch (Exception ignored) {
            }
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            long pageHeight = (long) jsExecutor.executeScript("return Math.max( document.body.scrollHeight"
                    + ", document.body.offsetHeight, document.documentElement.clientHeight,"
                    + " document.documentElement.scrollHeight,"
                    + " document.documentElement.offsetHeight )");
            int i = 0;
            try{
                WebElement noItem= driver.findElement(By.xpath("//h2[text()= 'No items found']"));
                if(noItem.isDisplayed()) return null;
            }
            catch(Exception ignored){
            }
            while (true) {
                long height = pageHeight * i;
                jsExecutor.executeScript("window.scrollTo(0, " + height + ");");
                try {
                    WebElement nextElement = driver.findElement(By.cssSelector("div.show-more"));
                    if (nextElement.isDisplayed()) {
                        Document parentDocument = Jsoup.parse(nextElement.getAttribute("outerHTML"));
                        Element element = parentDocument.select(":containsOwn(Load more)").first();
                        if (element != null) {
                            jsExecutor.executeScript("window.scrollTo(0 , -50);");
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    WebElement endElement = driver.findElement(By.xpath("//h2[text()='No more items']"));
                    if (endElement.isDisplayed())
                        break;
                } catch (Exception ignored) {
                }
                i++;
            }
            String html = (String) jsExecutor.executeScript("return document.documentElement.outerHTML");
            Document newDocument = Jsoup.parse(html);
            synchronized (HotTagCrawler.class) {
                if (document == null) {
                    document = newDocument.clone();
                } else {
                    Elements bodyElement = newDocument.body().children();
                    for (Element element : bodyElement) {
                        document.body().appendChild(element);
                    }
                }
            }
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
        }
    }
}
