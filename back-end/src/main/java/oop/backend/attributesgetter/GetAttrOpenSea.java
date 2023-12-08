package oop.backend.attributesgetter;

import oop.backend.dtos.OpenSeaDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAttrOpenSea implements AttrGetter<OpenSeaDTO> {
    @Override
    public OpenSeaDTO attrGet(Element element) {
        OpenSeaDTO openSea = new OpenSeaDTO();
        openSea.setImg(element.select("img").attr("src"));
        openSea.setName(element.select("span[data-id='TextBody']").select("div.bguyED").text());
        Elements priceElements =
            element.select("div.dYQlaY").select("span[data-id='TextBody']").select("div.axQXd");
        boolean isExists = priceElements.size() == 2;
        openSea.setVolume(isExists ? priceElements.get(0).text() : "");
        openSea.setFloorPrice(isExists ? priceElements.get(1).text() : "");
        if (openSea != null)
            return openSea;
        return null;
    }
}
