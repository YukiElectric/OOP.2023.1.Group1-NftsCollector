package oop.backend.crawler.trending;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.nftexchange.GetAttrRarible;
import oop.backend.crawler.crawlermethod.GetRarible;
import oop.backend.dtos.nftexchange.RaribleDTO;
import oop.backend.utils.jsonhandler.JsonHandlerUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.scroll.top.RaribleTopUtil;
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
@RequestMapping("${api.v1}/trending")
public class GetDataRaribleTrending extends GetRarible {
    private String PATH_RARIBLE = PathFixUtil.fix(App.class.getResource("/json/trending/rarible_data.json").getPath());
    private final AttrGetter<RaribleDTO> raribleAttr = new GetAttrRarible();

    public GetDataRaribleTrending(){
        selectionToRequest.put("Day", "?period=DAY");
        selectionToRequest.put("Week", "?period=WEEK");
        selectionToRequest.put("Month", "");
    }

    @Override
    public List<RaribleDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        Document document = RaribleTopUtil.scrollAndGet(request);

        Elements elements = document.select("div.sc-icLIcW");

        List<RaribleDTO> raribles = new ArrayList<>();

        for (Element element : elements) {
            RaribleDTO rarible = raribleAttr.attrGet(element);
            if (rarible != null && !raribles.contains(rarible))
                raribles.add(rarible);
        }
        return raribles;
    }

    private final JsonHandlerUtil<RaribleDTO> jsonHandler = new JsonHandlerUtil<>(PATH_RARIBLE);

    @GetMapping("/rarible/{selection}")
    public ResponseEntity<?> getDataRarible(@PathVariable("selection") String selection) {
        return jsonHandler.handleJsonOperation(() -> getData(selection));
    }
}
