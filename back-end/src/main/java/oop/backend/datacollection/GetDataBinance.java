package oop.backend.datacollection;

import oop.backend.attributesgetter.AttrGetter;
import oop.backend.attributesgetter.GetAttrBinance;
import oop.backend.dtos.BinanceDTO;
import oop.backend.utils.JsonHandlerUtil;
import oop.backend.utils.ScrollUtil;
import org.jsoup.Jsoup;
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
@RequestMapping("binance")
public class GetDataBinance implements DataCrawler {
//    private String PATH_BINANCE= App.class.getResource("/json/binance_data.json").toString();
private String PATH_BINANCE= "json/binance_data.json";
    private String url = "https://www.binance.com/en/nft/ranking?tab=collection";
    private final AttrGetter<BinanceDTO> binanceAttr = new GetAttrBinance();
    @Override
    public List<BinanceDTO> getData() throws Exception {
        String html = ScrollUtil.scroll(url);
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div.css-1gzgfll");
        
        List<BinanceDTO> binances = new ArrayList<>();
        for(Element element : elements){
            BinanceDTO binance = binanceAttr.attrGet(element);
            if(binance != null) binances.add(binance);
        }
        return binances;
    }
    private final JsonHandlerUtil<BinanceDTO> jsonHandler = new JsonHandlerUtil<>(PATH_BINANCE);
    @GetMapping("")
    public ResponseEntity<?> getDataFromBinance() {
        return jsonHandler.handleJsonOperation(() -> getData());
    }
}
