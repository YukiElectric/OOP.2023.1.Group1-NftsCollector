package oop.backend.crawler.trending;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.nftexchange.GetAttrOpenSea;
import oop.backend.crawler.crawlermethod.GetOpenSea;
import oop.backend.dtos.nftexchange.OpenSeaDTO;
import oop.backend.utils.jsonhandler.JsonHandlerUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.scroll.top.OpenSeaTopUtil;
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
public class GetDataOpenSeaTrending extends GetOpenSea {
    private final String PATH_OPEN_SEA = PathFixUtil.fix(App.class.getResource("/json/trending/open_sea_data.json").getPath());
    private final AttrGetter<OpenSeaDTO> openSeaAttr = new GetAttrOpenSea();

    public GetDataOpenSeaTrending() {
        selectionToRequest.put("Day", "?sortBy=one_day_volume");
        selectionToRequest.put("Week", "?sortBy=seven_day_volume");
    }
    @Override
    public List<OpenSeaDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        List<OpenSeaDTO> openSeas = new ArrayList<>();
        Document document = OpenSeaTopUtil.scrollAndGet(request);
        Elements elements = document.select("div.sc-e7b51c31-0");
        for (Element element : elements) {
            OpenSeaDTO openSea = openSeaAttr.attrGet(element);
            openSeas.add(openSea);
        }
        return openSeas;
    }
    private final JsonHandlerUtil<OpenSeaDTO> jsonHandler = new JsonHandlerUtil<>(PATH_OPEN_SEA);

    @GetMapping("/opensea/{selection}")
    public ResponseEntity<?> getDataFromOpenSea(@PathVariable("selection") String selection) {
        try {
            return jsonHandler.handleJsonOperation(() -> getData(selection));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }
}
