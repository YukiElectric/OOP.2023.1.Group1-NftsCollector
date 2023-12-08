package oop.backend.crawler.top;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.GetAttrNiftyGateway;
import oop.backend.crawler.DataCrawler;
import oop.backend.crawler.RequestList;
import oop.backend.dtos.NiftyGatewayDTO;
import oop.backend.utils.JsonHandlerUtil;
import oop.backend.utils.PathFixUtil;
import oop.backend.utils.sort.NiftyGatewaySortUtil;
import org.jsoup.Jsoup;
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
public class GetDataNiftyGateway extends RequestList implements DataCrawler {
    private String PATH_NIFTY_GATEWAY =
        PathFixUtil.fix(App.class.getResource("/json/nifty_gateway_data.json").getPath());
    
    private String url = "https://www.niftygateway.com/rankings";
    
    public GetDataNiftyGateway() {
        selectionToRequest.put("Day", "Last 24 Hours");
        selectionToRequest.put("Week", "Last 7 Days");
        selectionToRequest.put("Month", "Last 30 Days");
        selectionToRequest.put("AllTime", "All Time");
    }
    
    public List<NiftyGatewayDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        List<NiftyGatewayDTO> niftyGateways = new ArrayList<>();
        if (request != null) {
            Document document = Jsoup.parse(NiftyGatewaySortUtil.scrollAndGet(url, request));
            Elements elements = document.select("tr.css-x3ko2s");
            final AttrGetter<NiftyGatewayDTO> niftyGatewayAttr = new GetAttrNiftyGateway();
            
            for (Element element : elements) {
                NiftyGatewayDTO niftyGateway = niftyGatewayAttr.attrGet(element);
                
                if (niftyGateway != null)
                    niftyGateways.add(niftyGateway);
            }
        }
        return niftyGateways;
    }
    
    private final JsonHandlerUtil<NiftyGatewayDTO> jsonHandler = new JsonHandlerUtil<>(PATH_NIFTY_GATEWAY);
    
    @GetMapping("/niftygateway/{selection}")
    public ResponseEntity<?> getDataFromNiftyGateway(@PathVariable("selection") String selection) {
        try {
            return jsonHandler.handleJsonOperation(() -> getData(selection));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }
    
}
