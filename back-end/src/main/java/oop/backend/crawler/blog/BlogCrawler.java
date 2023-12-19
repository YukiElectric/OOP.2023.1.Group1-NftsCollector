package oop.backend.crawler.blog;

import oop.backend.App;
import oop.backend.config.PathFile;
import oop.backend.config.Url;
import oop.backend.properties.PropertyGetter;
import oop.backend.properties.blog.BlogBinanceProperty;
import oop.backend.dtos.blog.BlogDTO;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.json.JsonUtil;
import oop.backend.utils.scroll.blog.BlogUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("${api.v1}/blog")
public class BlogCrawler {
    private final String PATH_BLOG = PathFixUtil.fix(App.class.getResource(PathFile.PATH_BLOG).getPath());
    
    private List<BlogDTO> getData() throws Exception {
        List<BlogDTO> blogs = new ArrayList<>();
        String html = null;
        try {
            html = BlogUtil.getDoc(Url.URL_BINANCE_BLOG);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Document document = Jsoup.parse(html);
        final PropertyGetter<BlogDTO> blogBinanceAttr = new BlogBinanceProperty();
        Elements elements = document.select("div.css-1engawx").first().select("a.css-14ha60b");
        for (Element element : elements) {
            BlogDTO blog = blogBinanceAttr.attrGet(element);
            blogs.add(blog);
        }
        return blogs;
    }
    
    private JsonUtil<BlogDTO> jsonHandler = new JsonUtil<>(PATH_BLOG);
    
    @GetMapping("")
    public ResponseEntity<?> getBlog() {
        try {
            return jsonHandler.handleJsonOperation(() -> getData());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
