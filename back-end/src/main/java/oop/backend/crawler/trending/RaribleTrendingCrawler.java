package oop.backend.crawler.trending;

import oop.backend.App;
import oop.backend.config.PathFile;
import oop.backend.properties.PropertyGetter;
import oop.backend.properties.eplatform.RaribleProperty;
import oop.backend.crawler.abstractcrawler.GetRarible;
import oop.backend.dtos.eplatform.RaribleDTO;
import oop.backend.utils.json.JsonUtil;
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
public class RaribleTrendingCrawler extends GetRarible {
    private String PATH_RARIBLE = PathFixUtil.fix(App.class.getResource(PathFile.PATH_RARIBLE_TRENDING).getPath());
    PropertyGetter<RaribleDTO> raribleAttr = new RaribleProperty();

    public RaribleTrendingCrawler(){
        selectionToRequest.put("Day", "?period=DAY");
        selectionToRequest.put("Week", "?period=WEEK");
        selectionToRequest.put("Month", "");
        selectionToRequest.put("AllTime", "");
    }

    @Override
    public List<RaribleDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        Document document = RaribleTopUtil.scrollAndGet(request);
        Elements elements = document.select("div.sc-icLIcW");
        PropertyGetter<RaribleDTO> raribleAttr = new RaribleProperty();
        List<RaribleDTO> raribles = new ArrayList<>();
        for (Element element : elements) {
            RaribleDTO rarible = raribleAttr.attrGet(element);
            if (rarible != null && !raribles.contains(rarible))
                raribles.add(rarible);
        }
        return raribles;
    }

    private JsonUtil<RaribleDTO> jsonHandler = new JsonUtil<>(PATH_RARIBLE);

    @GetMapping("/rarible/{selection}")
    public ResponseEntity<?> getDataRarible(@PathVariable("selection") String selection) {
        return jsonHandler.handleJsonOperation(() -> getData(selection));
    }
}
