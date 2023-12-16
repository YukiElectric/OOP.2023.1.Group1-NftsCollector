package oop.backend.attributesgetter.blog;

import oop.backend.attributesgetter.AttrGetter;
import oop.backend.dtos.blog.BlogDTO;
import org.jsoup.nodes.Element;

public class GetAttrBlogBinance implements AttrGetter<BlogDTO> {
    @Override
    public BlogDTO attrGet(Element element) {
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setLinkBlog("https://www.binance.com/"+element.attr("href"));
        blogDTO.setImage(element.select("img").attr("src"));
        blogDTO.setTitle(element.select("h2.carousel-card-title").text());
        blogDTO.setTime(element.select("div.carousel-card-date").text());
        blogDTO.setArticle(element.select("div.carousel-card-content").text());
        return blogDTO;
    }
}
