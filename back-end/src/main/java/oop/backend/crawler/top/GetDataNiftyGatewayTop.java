package oop.backend.crawler.top;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.nftexchange.GetAttrNiftyGatewayTop;
import oop.backend.crawler.DataCrawler;
import oop.backend.crawler.RequestList;
import oop.backend.dtos.nftexchange.NiftyGatewayTopDTO;
import oop.backend.utils.jsonhandler.JsonHandlerUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.scroll.top.NiftyGatewayTopUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@Component
@RestController
@RequestMapping("${api.v1}/top")
public class GetDataNiftyGatewayTop extends RequestList implements DataCrawler {
    private String PATH_NIFTY_GATEWAY =
        PathFixUtil.fix(App.class.getResource("/json/top/nifty_gateway_data.json").getPath());
    
    public GetDataNiftyGatewayTop() {
        selectionToRequest.put("Day", "Last 24 Hours");
        selectionToRequest.put("Week", "Last 7 Days");
        selectionToRequest.put("Month", "Last 30 Days");
        selectionToRequest.put("AllTime", "All Time");
    }
    
    public List<NiftyGatewayTopDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        List<NiftyGatewayTopDTO> niftyGateways = new ArrayList<>();
        if (request != null) {
            Document document = NiftyGatewayTopUtil.scrollAndGet( request);
            Elements elements = document.select("tr.css-x3ko2s");
            final AttrGetter<NiftyGatewayTopDTO> niftyGatewayAttr = new GetAttrNiftyGatewayTop();
            
            for (Element element : elements) {
                NiftyGatewayTopDTO niftyGateway = niftyGatewayAttr.attrGet(element);
                
                if (niftyGateway != null)
                    niftyGateways.add(niftyGateway);
            }
        }
        return niftyGateways;
    }
    
    private final JsonHandlerUtil<NiftyGatewayTopDTO> jsonHandler = new JsonHandlerUtil<>(PATH_NIFTY_GATEWAY);
    
    @GetMapping("/niftygateway/{selection}")
    public ResponseEntity<?> getDataFromNiftyGateway(@PathVariable("selection") String selection) {
        try {
            return jsonHandler.handleJsonOperation(() -> getData(selection));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }


}
