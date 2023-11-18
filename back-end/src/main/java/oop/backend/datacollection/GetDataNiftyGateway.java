package oop.backend.datacollection;

import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.GetAttrNiftyGateway;
import oop.backend.dtos.NiftyGatewayDTO;
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

import java.util.List;
import java.util.ArrayList;

@Component
@RestController
@RequestMapping("niftygateway")
public class GetDataNiftyGateway implements DataCrawler{
    @Value("${PATH_NIFTY_GATEWAY}")
    private String  PATH_NIFTY_GATEWAY;
    private String url = "https://www.niftygateway.com/marketplace";
    private final AttrGetter<NiftyGatewayDTO> niftyGatewayAttr = new GetAttrNiftyGateway();
    public List<NiftyGatewayDTO> getData() throws Exception {
        
        String html = ScrollUtil.scroll(url);
        
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div.css-jj9f9r");
        
        List<NiftyGatewayDTO> niftyGateways = new ArrayList<>();
        
        for(Element element : elements){
            NiftyGatewayDTO niftyGateway = niftyGatewayAttr.attrGet(element);
            if(niftyGateway != null) niftyGateways.add(niftyGateway);
        }
        return niftyGateways;
    }
    
    private final JsonHandlerUtil<NiftyGatewayDTO> jsonHandler = new JsonHandlerUtil<>(PATH_NIFTY_GATEWAY);
    @GetMapping("")
    public ResponseEntity<?> getDataFromNiftyGateway() {
        return jsonHandler.handleJsonOperation(() -> getData());
    }
    
}
