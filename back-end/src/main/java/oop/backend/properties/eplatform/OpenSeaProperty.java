package oop.backend.properties.eplatform;

import oop.backend.properties.PropertyGetter;
import oop.backend.dtos.eplatform.OpenSeaDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OpenSeaProperty implements PropertyGetter<OpenSeaDTO> {
    @Override
    public OpenSeaDTO attrGet(Element element) {
        OpenSeaDTO openSea = new OpenSeaDTO();
        openSea.setCollection(element.select("span[data-id='TextBody']").select("div.bguyED").text());
        Elements priceElements =
            element.select("div.dYQlaY").select("span[data-id='TextBody']").select("div.axQXd");
        boolean isExists = priceElements.size() == 2;
        openSea.setVolume(isExists ? priceElements.get(0).text() : "");
        openSea.setFloorPrice(isExists ? priceElements.get(1).text() : "");
        return openSea;
    }
}
