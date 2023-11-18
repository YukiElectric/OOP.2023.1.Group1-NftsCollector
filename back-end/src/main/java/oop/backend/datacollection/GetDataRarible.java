package oop.backend.datacollection;

import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.GetAttrRarible;
import oop.backend.dtos.RaribleDTO;
import oop.backend.exceptions.BadRequestException;
import oop.backend.utils.JsonHandlerUtil;
import oop.backend.utils.ScrollUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@Component
@RestController
@RequestMapping("rarible")
public class GetDataRarible implements DataCrawler{
    @Value("${PATH_RARIBLE}")
    private String  PATH_RARIBLE;
    private String url = "https://rarible.com/explore/ethereum/collections?period=MONTH";
    private final AttrGetter<RaribleDTO> raribleAttr = new GetAttrRarible();
    public List<RaribleDTO> getData() throws BadRequestException, InterruptedException {
        String html = ScrollUtil.scroll(url);
        
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
    
    @GetMapping("")
    public ResponseEntity<?> getDataRarible() {
        return jsonHandler.handleJsonOperation(() -> getData());
    }
}
