package oop.backend.attributesgetter;

import oop.backend.dtos.TwitterDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAttrTwitter implements AttrGetter<TwitterDTO> {
    @Override
    public TwitterDTO attrGet(Element element) {
        TwitterDTO twitter = new TwitterDTO();
        twitter.setImage(element.select("div[data-testid='tweetPhoto']").select("img").attr("src"));
        Elements tweetNames = element.select("div[data-testid='User-Name']").select("div.css-1hf3ou5");
        String name = "";
        for (Element tweetName : tweetNames) {
            name += tweetName.text();
        }
        twitter.setUser(name);
        twitter.setHashtag(element.select("div.r-18u37iz").select("a[href^=/hashtag/]").text());
        twitter.setContent(element.select("div[data-testid='tweetText']").text());
        if (!twitter.getUser().isEmpty()) return twitter;
        return null;
    }
}
