package oop.backend.crawler.top;

import oop.backend.App;
import oop.backend.config.PathFile;
import oop.backend.properties.PropertyGetter;
import oop.backend.properties.eplatform.BinanceProperty;
import oop.backend.crawler.abstractcrawler.GetBinance;
import oop.backend.dtos.eplatform.BinanceDTO;
import oop.backend.utils.json.JsonUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.scroll.top.BinanceTopUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("${api.v1}/top")
public class BinanceTopCrawler extends GetBinance {
    private final String PATH_BINANCE = PathFixUtil.fix(App.class.getResource(PathFile.PATH_BINANCE_TOP).getPath());
    @Override
    public List<BinanceDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        List<BinanceDTO> binances = new ArrayList<>();
        if (request != null) {
            Document document = Jsoup.parse(BinanceTopUtil.scrollAndGet(request));
            Elements elements = document.select("div.css-vurnku");
            PropertyGetter<BinanceDTO> binanceAttr = new BinanceProperty();
            for (Element element : elements) {
                BinanceDTO binance = binanceAttr.attrGet(element);
                if (binance != null && !binances.contains(binance))
                    binances.add(binance);
            }
        }
        return binances;
    }
    public BinanceTopCrawler() {
        selectionToRequest.put("Day", "24H");
        selectionToRequest.put("Week", "7D");
        selectionToRequest.put("Month", "30D");
        selectionToRequest.put("AllTime", "All");
    }
    private JsonUtil<BinanceDTO> jsonHandler = new JsonUtil<>(PATH_BINANCE);

    @GetMapping("/binance/{selection}")
    public ResponseEntity<?> getDataFromBinance(@PathVariable("selection") String selection) {
        try{
            return jsonHandler.handleJsonOperation(() -> getData(selection));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }

}
