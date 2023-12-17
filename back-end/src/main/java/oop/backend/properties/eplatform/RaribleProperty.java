package oop.backend.properties.eplatform;

import oop.backend.properties.PropertyGetter;
import oop.backend.dtos.eplatform.RaribleDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RaribleProperty implements PropertyGetter<RaribleDTO> {
    @Override
    public RaribleDTO attrGet(Element element) {
        RaribleDTO rarible = new RaribleDTO();
        rarible.setName(element.select("div.sc-dmLtQE").select("span").text());
        Elements priceElements = element.select("span.sc-ktEKTO");
        boolean isExists = priceElements.size() == 2;
        rarible.setVolume(isExists ? priceElements.get(1).text() : "");
        rarible.setFloorPrice(isExists ? priceElements.get(0).text() : "");
        rarible.setItems(element.select("div.ggUcdJ").text());
        rarible.setOwners(element.select("div.keSOtI").text());
        if (isExists)
            return rarible;
        return null;
    }
}
