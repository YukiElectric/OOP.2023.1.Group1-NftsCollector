package oop.backend.crawler.post;

import oop.backend.App;
import oop.backend.config.PathFile;
import oop.backend.config.Url;
import oop.backend.properties.PropertyGetter;
import oop.backend.properties.post.TwitterProperty;
import oop.backend.crawler.abstractcrawler.GetTwitter;
import oop.backend.dtos.post.TwitterDTO;
import oop.backend.utils.json.JsonUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.fix.QueryFixUtil;
import oop.backend.utils.scroll.post.TwitterSearchUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("${api.v1}/post")
public class TwitterCrawler extends GetTwitter {
    private String PATH_TWITTER = PathFixUtil.fix(App.class.getResource(PathFile.PATH_POST).getPath());

    public List<TwitterDTO> getData(String selection) throws Exception {
        String request;
        if(selection != null) {
            request = "%23"+QueryFixUtil.fix(selection);}
        else request = Url.URL_TWITTER_DEFAULT;
        List<TwitterDTO> twitters = new ArrayList<>();
        PropertyGetter<TwitterDTO> twitterAttr = new TwitterProperty();
        Document document = TwitterSearchUtil.getByTag(request);
        Elements elements = document.select("div[data-testid='cellInnerDiv']");
        for (Element element : elements) {
            TwitterDTO twitter = twitterAttr.attrGet(element);
            if (twitter != null)
                twitters.add(twitter);
        }
        return twitters;
    }

    private JsonUtil<TwitterDTO> jsonHandler = new JsonUtil<>(PATH_TWITTER);

    @GetMapping("/twitter/{selection}")
    public ResponseEntity<?> getDataFromTwitter(@PathVariable("selection") String selection) {
        try{
            return jsonHandler.handleJsonOperation(() -> getData(selection));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }

}

