package oop.backend.crawler.blog;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.blog.GetAttrBlogBinance;
import oop.backend.attributesgetter.blog.GetAttrBlogOpenSea;
import oop.backend.dtos.blog.BlogDTO;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.jsonhandler.JsonHandlerUtil;
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
public class GetDataBlog {
    private final String PATH_BLOG = PathFixUtil.fix(App .class.getResource("/json/blog/blog_data.json").getPath());
    List<BlogDTO> blogs = new ArrayList<>();
    class GetBlog extends Thread {
        @Override
        public void run() {
            String urlBlog = "https://www.binance.com/en/blog";
            String html = null;
            try {
                html = BlogUtil.getDoc(urlBlog);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Document document = Jsoup.parse(html);
            synchronized (GetDataBlog.class) {
                final AttrGetter<BlogDTO> blogBinanceAttr = new GetAttrBlogBinance();
                Elements elements = document.select("div.css-1engawx").first().select("a.css-14ha60b");
                for(Element element : elements) {
                    BlogDTO blog = blogBinanceAttr.attrGet(element);
                    blogs.add(blog);
                }
            }
        }
    }
    private List<BlogDTO> getBlogs(){
        return this.blogs;
    }
    private void getData() throws Exception{
        GetBlog thread = new GetBlog();
        thread.start();
        final String url = "https://opensea.io/blog?30f2d4c5_page=";
        for (int i = 1; i < 10; i++) {
            Document document = Jsoup.connect(url+i).userAgent("Jsoup client").get();
            final AttrGetter<BlogDTO> blogOpenSeaAttr = new GetAttrBlogOpenSea();
            Elements elements = document.select("div.blog-card");
            for (Element element : elements) {
                BlogDTO blog = blogOpenSeaAttr.attrGet(element);
                blogs.add(blog);
            }
        }
        thread.join();
        Comparator<BlogDTO> sort = Comparator.comparing(s -> LocalDate.parse(s.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        blogs.sort(sort.reversed());
    }
    private final JsonHandlerUtil<BlogDTO> jsonHandler = new JsonHandlerUtil<>(PATH_BLOG);
    @GetMapping("")
    public ResponseEntity<?> getBlog() {
        try {
            return jsonHandler.handleJsonOperation(()->getBlogs());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
