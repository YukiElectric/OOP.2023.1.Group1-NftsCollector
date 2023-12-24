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
            element.select("span[data-id='TextBody']");
        boolean isExists = priceElements.size() == 10;
        openSea.setNo(isExists ? priceElements.get(0).text() : "");
        openSea.setVolume(isExists ? priceElements.get(2).text() : "");
        openSea.setVolumeChange(isExists ? priceElements.get(3).text() : "");
        openSea.setFloorPrice(isExists ? priceElements.get(4).text() : "");
        openSea.setSales(isExists ? priceElements.get(5).text() : "");
        openSea.setOwners(isExists ? priceElements.get(7).text() : "");
        openSea.setItems(isExists ? priceElements.get(9).text() : "");
        return openSea;
    }
}
