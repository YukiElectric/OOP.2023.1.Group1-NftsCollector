package oop.backend;

import lombok.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.http.ResponseEntity;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NiftyGateway {
    private String name;
    private String floorPrice;
    private String image;
    private String author;
    private String edition;
}
