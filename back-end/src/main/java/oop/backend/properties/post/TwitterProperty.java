package oop.backend.properties.post;

import oop.backend.properties.PropertyGetter;
import oop.backend.dtos.post.TwitterDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TwitterProperty implements PropertyGetter<TwitterDTO> {
    @Override
    public TwitterDTO attrGet(Element element) {
        TwitterDTO twitter = new TwitterDTO();
        twitter.setImage(element.select("div[data-testid='tweetPhoto']").select("img").attr("src"));
        Elements tweetNames = element.select("div[data-testid='User-Name']").select("div.r-1wvb978");
        String name = "";
        for (Element tweetName : tweetNames) {
            name += tweetName.text();
        }
        twitter.setUser(name);
        twitter.setHashtag(element.select("div.r-18u37iz").select("a[href^=/hashtag/]").text());
        twitter.setContent(element.select("div[data-testid='tweetText']").text());
        if (!twitter.getUser().isEmpty())
            return twitter;
        return null;
    }
}
