package oop.backend.attributesgetter;

import oop.backend.dtos.RaribleDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAttrRarible implements AttrGetter<RaribleDTO> {
    @Override
    public RaribleDTO attrGet(Element element) {
        RaribleDTO rarible = new RaribleDTO();
        rarible.setImg(element.select("div.sc-hyBYnz").select("img").attr("src"));
        rarible.setName(element.select("div.sc-dmLtQE").select("span").text());
        Elements priceElements = element.select("span.sc-ktEKTO");
        boolean isExists = priceElements.size() == 2;
        rarible.setVolume(isExists ? priceElements.get(1).text() : "");
        rarible.setFloorPrice(isExists ? priceElements.get(0).text() : "");
        if (isExists)
            return rarible;
        return null;
    }
}
