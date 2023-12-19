package oop.backend.crawler.trending;

import oop.backend.App;
import oop.backend.config.PathFile;
import oop.backend.config.Url;
import oop.backend.properties.PropertyGetter;
import oop.backend.properties.eplatform.NiftyTrendingProperty;
import oop.backend.crawler.abstractcrawler.GetNiftyTrending;
import oop.backend.dtos.eplatform.NiftyTrendingDTO;
import oop.backend.utils.json.JsonUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.scroll.trending.NiftyTrendingUtil;
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
public class NiftyTrendingCrawler extends GetNiftyTrending {
    private String PATH_NIFTY_GATEWAY =
            PathFixUtil.fix(App.class.getResource(PathFile.PATH_NIFTY_TRENDING).getPath());

    private JsonUtil<NiftyTrendingDTO> jsonHandler = new JsonUtil<>(PATH_NIFTY_GATEWAY);

    public NiftyTrendingCrawler(){
        selectionToRequest.put("Day", "");
        selectionToRequest.put("Week", "");
        selectionToRequest.put("Month", "");
        selectionToRequest.put("AllTime", "");
    }

    @Override
    public List<NiftyTrendingDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        PropertyGetter<NiftyTrendingDTO> niftyGatewayAttr = new NiftyTrendingProperty();
        List<NiftyTrendingDTO> niftyGateways = new ArrayList<>();
        Document document = NiftyTrendingUtil.scrollAndGet(request);
        Elements elements = document.select("div.css-jj9f9r");
        for (Element element : elements) {
            NiftyTrendingDTO niftyGateway = niftyGatewayAttr.attrGet(element);
            if (niftyGateway != null && !niftyGateways.contains(niftyGateway))
                niftyGateways.add(niftyGateway);
        }
        return niftyGateways;
    }
    @GetMapping("/niftygateway/{selection}")
    public ResponseEntity<?> getDataFromNiftyGateway(@PathVariable("selection") String selection) {
        try {
            return jsonHandler.handleJsonOperation(() -> getData(selection));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }
}

