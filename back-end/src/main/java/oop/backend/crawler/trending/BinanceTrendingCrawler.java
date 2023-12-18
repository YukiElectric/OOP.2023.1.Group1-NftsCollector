package oop.backend.crawler.trending;

import oop.backend.App;
import oop.backend.config.PathFile;
import oop.backend.properties.PropertyGetter;
import oop.backend.properties.eplatform.BinanceProperty;
import oop.backend.crawler.abstractcrawler.GetBinance;
import oop.backend.dtos.eplatform.BinanceDTO;
import oop.backend.utils.json.JsonUtil;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.scroll.trending.BinanceTrendingUtil;
import org.jsoup.Jsoup;
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
public class BinanceTrendingCrawler extends GetBinance {
    private final String PATH_BINANCE = PathFixUtil.fix(App.class.getResource(PathFile.PATH_BINANCE_TRENDING).getPath());
    @Override
    public List<BinanceDTO> getData(String selection) throws Exception {
        String request = selectionToRequest.get(selection);
        List<BinanceDTO> binances = new ArrayList<>();
        if (request != null) {
            Document document = Jsoup.parse(BinanceTrendingUtil.scrollAndGet(request));
            Elements elements = document.select("div.css-vurnku");
            final PropertyGetter<BinanceDTO> binanceAttr = new BinanceProperty();
            for (Element element : elements) {
                BinanceDTO binance = binanceAttr.attrGet(element);
                if (binance != null && !binances.contains(binance))
                    binances.add(binance);
            }
        }
        return binances;
    }
    public BinanceTrendingCrawler() {
        selectionToRequest.put("Day", "24H");
        selectionToRequest.put("Week", "7D");
    }
    private final JsonUtil<BinanceDTO> jsonHandler = new JsonUtil<>(PATH_BINANCE);

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
