package oop.backend;

import oop.backend.dtos.post.DetailTagDTO;
import oop.backend.dtos.post.HotTagDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("hottags")
public class GetHotHastag {
    
    private Document getDocument(String input) throws Exception {
        String url = "https://displaypurposes.com/hashtags/hashtag/";
        return Jsoup.connect(url+input).userAgent("Jsoup client").get();
    }
    
    private String getPost(String url) throws Exception {
        Element element = getDocument(url).select("div.posts-info").first();
        return element.child(0).text().replace(" ","");
    }
    
    private List<DetailTagDTO> getDetail(String url) throws Exception {
        Elements elements = getDocument(url).select("div.value-wrapper").first().select("div.value-item");
        List<DetailTagDTO> detailTags = new ArrayList<>();
        for(Element element : elements) {
            DetailTagDTO detailTag = new DetailTagDTO();
            List<String> value = List.of(element.text().replace(" ", "").split("-"));
            detailTag.setTagDetail(value.get(0));
            detailTag.setPercent(value.get(1));
            detailTags.add(detailTag);
        }
        return detailTags;
    }
    @GetMapping("")
    public ResponseEntity<?> getHotTag(){
        try {
            List<HotTagDTO> hotTags = new ArrayList<>();
            String[] tags = {"nft","opensea","binance"};
            for (String tag : tags) {
                HotTagDTO hotTag = new HotTagDTO();
                hotTag.setTag(tag);
                hotTag.setTotalPost(Integer.parseInt(getPost(tag)));
                hotTag.setDetailTag(getDetail(tag));
                hotTags.add(hotTag);
            }
            return ResponseEntity.ok(hotTags);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
