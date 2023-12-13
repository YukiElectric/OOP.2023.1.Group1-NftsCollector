package oop.backend.attributesgetter.nftexchange;

import oop.backend.attributesgetter.AttrGetter;
import oop.backend.dtos.nftexchange.BinanceDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAttrBinance implements AttrGetter<BinanceDTO> {
    @Override
    public BinanceDTO attrGet(Element element) {
        BinanceDTO binance = new BinanceDTO();
        binance.setName(element.select("div.css-1iqk42z").text());
        Elements priceElements = element.select("div.css-9bhndi");
        boolean isExists = priceElements.size() == 2;
        binance.setVolume(isExists ? priceElements.get(0).text() : "");
        binance.setFloorPrice(isExists ? priceElements.get(1).text() : "");
        Elements countElements = element.select("div.css-15n6x8q");
        boolean ifExists = countElements.size() == 2;
        binance.setItems(ifExists ? countElements.get(0).text() : "");
        binance.setOwners(ifExists ? countElements.get(1).text() : "");
        if (isExists)
            return binance;
        return null;
    }
}
