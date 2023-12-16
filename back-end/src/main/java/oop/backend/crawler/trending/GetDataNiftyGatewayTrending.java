package oop.backend.crawler.trending;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.nftexchange.GetAttrNiftyGatewayTrending;
import oop.backend.crawler.crawlermethod.GetNiftyGatewayTrending;
import oop.backend.dtos.nftexchange.NiftyGatewayTrendingDTO;
import oop.backend.utils.jsonhandler.JsonHandlerUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.scroll.trending.NiftyGatewayTrendingUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("${api.v1}/trending/niftygateway")
public class GetDataNiftyGatewayTrending extends GetNiftyGatewayTrending {
    private String PATH_NIFTY_GATEWAY =
            PathFixUtil.fix(App.class.getResource("/json/trending/nifty_gateway_data.json").getPath());
    private final AttrGetter<NiftyGatewayTrendingDTO> niftyGatewayAttr = new GetAttrNiftyGatewayTrending();

    @Override
    public List<NiftyGatewayTrendingDTO> getData() throws Exception {
        String url = "https://www.niftygateway.com/marketplace?sortBy=mostLiked";
        List<NiftyGatewayTrendingDTO> niftyGateways = new ArrayList<>();
        Document document = NiftyGatewayTrendingUtil.scrollAndGet(url);
        Elements elements = document.select("div.css-jj9f9r");
        for (Element element : elements) {
            NiftyGatewayTrendingDTO niftyGateway = niftyGatewayAttr.attrGet(element);

            if (niftyGateway != null)
                niftyGateways.add(niftyGateway);
        }
        return niftyGateways;
    }

    private final JsonHandlerUtil<NiftyGatewayTrendingDTO> jsonHandler = new JsonHandlerUtil<>(PATH_NIFTY_GATEWAY);

    @GetMapping("")
    public ResponseEntity<?> getDataFromNiftyGateway() {
        try {
            return jsonHandler.handleJsonOperation(() -> getData());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }
}
