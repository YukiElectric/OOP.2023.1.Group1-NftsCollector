package oop.backend.attributesgetter.blog;

import oop.backend.attributesgetter.AttrGetter;
import oop.backend.dtos.blog.BlogDTO;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class GetAttrBlogOpenSea implements AttrGetter<BlogDTO> {
    @Override
    public BlogDTO attrGet(Element element) {
        BlogDTO blog = new BlogDTO();
        blog.setLinkBlog("https://opensea.io"+element.select("a").first().attr("href"));
        blog.setImage(element.select("img").attr("src"));
        blog.setLabel(Arrays.stream(element.select("div.card_label-wrapper").text().split(" ")).toList());
        blog.setTitle(element.select("h3.card_articles-title").text());
        String dateString = element.select("div.card_articles-date").first().text();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDate date = LocalDate.parse(dateString, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(outputFormatter);
        blog.setTime(formattedDate);
        blog.setArticle(element.select("p.card_summary-article").text().replace("\"",""));
        return blog;
    }
}
