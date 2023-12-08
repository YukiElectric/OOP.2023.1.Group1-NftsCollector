package oop.backend.crawler.top;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.GetAttrBinance;
import oop.backend.crawler.DataCrawler;
import oop.backend.crawler.RequestList;
import oop.backend.dtos.BinanceDTO;
import oop.backend.utils.JsonHandlerUtil;
import oop.backend.utils.PathFixUtil;
import oop.backend.utils.sort.BinanceSortUtil;
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
@RequestMapping("${api.v1}/binance")
public class GetDataBinance extends RequestList implements DataCrawler {
    private final String PATH_BINANCE = PathFixUtil.fix(App.class.getResource("/json/binance_data.json").getPath());
    
    private String url = "https://www.binance.com/en/nft/ranking?tab=collection";
    
    public GetDataBinance() {
        selectionToRequest.put("Day", "24H");
        selectionToRequest.put("Week", "7D");
        selectionToRequest.put("Month", "30D");
        selectionToRequest.put("AllTime", "All");
    }
    
    @Override
    public List<BinanceDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        List<BinanceDTO> binances = new ArrayList<>();
        if (request != null) {
            Document document = Jsoup.parse(BinanceSortUtil.scrollAndGet(url, request));
            Elements elements = document.select("div.css-1gzgfll");
            final AttrGetter<BinanceDTO> binanceAttr = new GetAttrBinance();
            for (Element element : elements) {
                BinanceDTO binance = binanceAttr.attrGet(element);
                if (binance != null)
                    binances.add(binance);
            }
        }
        return binances;
    }
    
    private final JsonHandlerUtil<BinanceDTO> jsonHandler = new JsonHandlerUtil<>(PATH_BINANCE);
    
    @GetMapping("/{selection}")
    public ResponseEntity<?> getDataFromBinance(@PathVariable("selection") String selection) {
        try{
            return jsonHandler.handleJsonOperation(() -> getData(selection));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }
    
}
