package oop.backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TwitterSearch {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

        // Khởi tạo trình duyệt
        WebDriver driver = new FirefoxDriver();

        // Mở trang Twitter
        driver.get("https://twitter.com/login");

        // Đăng nhập (thay thế 'USERNAME' và 'PASSWORD' bằng thông tin tài khoản Twitter của bạn)
        WebElement usernameInput = driver.findElement(By.cssSelector("input[autocomplete='username']"));
        usernameInput.sendKeys("yukiELectric72");
        WebElement divElement = driver.findElement(By.cssSelector("div[dir='ltr']"));
        divElement.click();
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement password = driver.findElement(By.cssSelector("input[autocomplete='current-password']"));
        password.sendKeys("07022003");

        WebElement loginButton = driver.findElement(By.cssSelector("div[dir='ltr']"));
        loginButton.click();

        // Chờ đợi một thời gian ngắn để đăng nhập
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Tìm kiếm theo hashtag "nft"
        WebElement searchBox = driver.findElement(By.xpath("//input[@data-testid='SearchBox_Search_Input']"));
        searchBox.sendKeys("#nft");
        searchBox.submit();

        // Chờ đợi một khoảng thời gian để cho kết quả hiển thị
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // In ra tiêu đề của trang hiện tại (có thể thay đổi để lấy dữ liệu khác)
        System.out.println(driver.getTitle());

        // Đóng trình duyệt
        driver.quit();
    }
}
