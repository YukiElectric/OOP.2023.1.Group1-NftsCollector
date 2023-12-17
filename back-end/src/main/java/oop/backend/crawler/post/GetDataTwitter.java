package oop.backend.crawler.post;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.post.GetAttrTwitter;
import oop.backend.crawler.crawlermethod.GetTwitter;
import oop.backend.dtos.post.TwitterDTO;
import oop.backend.utils.jsonhandler.JsonHandlerUtil;
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
public class GetDataTwitter extends GetTwitter {
    private String PATH_TWITTER = PathFixUtil.fix(App.class.getResource("/json/post/twitter_data.json").getPath());

    public List<TwitterDTO> getData(String selection) throws Exception {
        String request = "";
        if(selection != null) {
            request = "%23"+QueryFixUtil.fix(selection);}
        else request = "%23nft";
        List<TwitterDTO> twitters = new ArrayList<>();
        final AttrGetter<TwitterDTO> twitterAttr = new GetAttrTwitter();
        Document document = TwitterSearchUtil.getByTag(request);
        Elements elements = document.select("div[data-testid='cellInnerDiv']");
        for (Element element : elements) {
            TwitterDTO twitter = twitterAttr.attrGet(element);
            if (twitter != null)
                twitters.add(twitter);
        }
        return twitters;
    }

    private final JsonHandlerUtil<TwitterDTO> jsonHandler = new JsonHandlerUtil<>(PATH_TWITTER);

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

