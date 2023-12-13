package oop.backend;

import oop.backend.dtos.blog.BlogDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("blog")
public class Blog {
    private List<BlogDTO> getData() throws Exception{
        final String url = "https://opensea.io/blog?30f2d4c5_page=";
        final String urlBlog = "https://opensea.io";
        List<BlogDTO> blogs = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Document document = Jsoup.connect(url+i).userAgent("Jsoup client").get();
            Elements elements = document.select("div.blog-card");
            for (Element element : elements) {
                BlogDTO blog = new BlogDTO();
                blog.setLinkBlog(urlBlog+element.select("a").first().attr("href"));
                blog.setImage(element.select("img").attr("src"));
                blog.setLabel(Arrays.stream(element.select("div.card_label-wrapper").text().split(" ")).toList());
                blog.setTitle(element.select("h3.card_articles-title").text());
                blog.setTime(element.select("div.card_articles-date").first().text());
                blog.setArticle(element.select("p.card_summary-article").text().replace("\"",""));
                blogs.add(blog);
            }
        }
        return blogs;
    }
    
    @RequestMapping("")
    public ResponseEntity<?> getBlog() {
        try {
            return ResponseEntity.ok(getData());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
