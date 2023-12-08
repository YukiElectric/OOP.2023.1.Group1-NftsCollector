package oop.backend.crawler.top;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.GetAttrRarible;
import oop.backend.crawler.DataCrawler;
import oop.backend.dtos.RaribleDTO;
import oop.backend.utils.JsonHandlerUtil;
import oop.backend.utils.PathFixUtil;
import oop.backend.utils.ScrollUtil;
import org.jsoup.Jsoup;
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
@RequestMapping("${api.v1}/top")
public class GetDataRarible implements DataCrawler {
    private String PATH_RARIBLE = PathFixUtil.fix(App.class.getResource("/json/rarible_data.json").getPath());
    
    private String url = "https://rarible.com/explore/ethereum/collections?period=MONTH";
    
    private final AttrGetter<RaribleDTO> raribleAttr = new GetAttrRarible();
    
    public List<RaribleDTO> getData(@PathVariable("request") String request) throws InterruptedException {
        String html = ScrollUtil.scrollUseURL(url);
        
        Document document = Jsoup.parse(html);
        
        Elements elements = document.select("div.sc-icLIcW");
        
        List<RaribleDTO> raribles = new ArrayList<>();
        
        for (Element element : elements) {
            RaribleDTO rarible = raribleAttr.attrGet(element);
            if (rarible != null)
                raribles.add(rarible);
        }
        return raribles;
    }
    
    private final JsonHandlerUtil<RaribleDTO> jsonHandler = new JsonHandlerUtil<>(PATH_RARIBLE);
    
    @GetMapping("/rarible/{selection}")
    public ResponseEntity<?> getDataRarible() {
        return jsonHandler.handleJsonOperation(() -> getData(""));
    }
}
