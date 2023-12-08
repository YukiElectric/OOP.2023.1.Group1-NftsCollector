package oop.backend.attributesgetter;

import oop.backend.dtos.BinanceDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAttrBinance implements AttrGetter<BinanceDTO> {
    @Override
    public BinanceDTO attrGet(Element element) {
        BinanceDTO binance = new BinanceDTO();
        binance.setImg(element.select("img").attr("src"));
        binance.setName(element.select("div.css-31460s").text());
        Elements priceElements = element.select("div.css-9w1gf");
        boolean isExists = priceElements.size() == 2;
        binance.setVolume(isExists ? priceElements.get(0).text() : "");
        binance.setFloorPrice(isExists ? priceElements.get(1).text() : "");
        if (isExists)
            return binance;
        return null;
    }
}
