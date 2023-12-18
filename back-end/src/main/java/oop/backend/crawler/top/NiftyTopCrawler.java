package oop.backend.crawler.top;

import oop.backend.App;
import oop.backend.config.PathFile;
import oop.backend.properties.PropertyGetter;
import oop.backend.properties.eplatform.NiftyTopProperty;
import oop.backend.crawler.DataCrawler;
import oop.backend.crawler.RequestList;
import oop.backend.dtos.eplatform.NiftyTopDTO;
import oop.backend.utils.json.JsonUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.scroll.top.NiftyTopUtil;
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
public class NiftyTopCrawler extends RequestList implements DataCrawler {
    private String PATH_NIFTY_GATEWAY =
        PathFixUtil.fix(App.class.getResource(PathFile.PATH_NIFTY_TOP).getPath());
    
    public NiftyTopCrawler() {
        selectionToRequest.put("Day", "Last 24 Hours");
        selectionToRequest.put("Week", "Last 7 Days");
        selectionToRequest.put("Month", "Last 30 Days");
        selectionToRequest.put("AllTime", "All Time");
    }
    
    public List<NiftyTopDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        List<NiftyTopDTO> niftyGateways = new ArrayList<>();
        if (request != null) {
            Document document = NiftyTopUtil.scrollAndGet( request);
            Elements elements = document.select("tr.css-x3ko2s");
            final PropertyGetter<NiftyTopDTO> niftyGatewayAttr = new NiftyTopProperty();
            
            for (Element element : elements) {
                NiftyTopDTO niftyGateway = niftyGatewayAttr.attrGet(element);

                if (niftyGateway != null && !niftyGateways.contains(niftyGateway))
                    niftyGateways.add(niftyGateway);
            }
        }
        return niftyGateways;
    }
    
    private final JsonUtil<NiftyTopDTO> jsonHandler = new JsonUtil<>(PATH_NIFTY_GATEWAY);
    
    @GetMapping("/niftygateway/{selection}")
    public ResponseEntity<?> getDataFromNiftyGateway(@PathVariable("selection") String selection) {
        try {
            return jsonHandler.handleJsonOperation(() -> getData(selection));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }


}
