package oop.backend.crawler.top;

import oop.backend.App;
import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.nftexchange.GetAttrBinance;
import oop.backend.crawler.crawlermethod.GetBinance;
import oop.backend.dtos.nftexchange.BinanceDTO;
import oop.backend.utils.jsonhandler.JsonHandlerUtil;
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
public class GetDataBinanceTop extends GetBinance {
    private final String PATH_BINANCE = PathFixUtil.fix(App.class.getResource("/json/top/binance_data.json").getPath());
    @Override
    public List<BinanceDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        List<BinanceDTO> binances = new ArrayList<>();
        if (request != null) {
            Document document = Jsoup.parse(BinanceTopUtil.scrollAndGet(request));
            Elements elements = document.select("div.css-vurnku");
            final AttrGetter<BinanceDTO> binanceAttr = new GetAttrBinance();
            for (Element element : elements) {
                BinanceDTO binance = binanceAttr.attrGet(element);
                if (binance != null && !binances.contains(binance))
                    binances.add(binance);
            }
        }
        return binances;
    }
    public GetDataBinanceTop() {
        selectionToRequest.put("Day", "24H");
        selectionToRequest.put("Week", "7D");
        selectionToRequest.put("Month", "30D");
        selectionToRequest.put("AllTime", "All");
    }
    private final JsonHandlerUtil<BinanceDTO> jsonHandler = new JsonHandlerUtil<>(PATH_BINANCE);

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
