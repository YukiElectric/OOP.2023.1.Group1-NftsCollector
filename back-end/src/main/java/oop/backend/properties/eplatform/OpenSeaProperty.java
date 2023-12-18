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
            element.select("span[data-id='TextBody']").select("div.axQXd");
        boolean isExists = priceElements.size() == 7;
        openSea.setVolume(isExists ? priceElements.get(1).text() : "");
        openSea.setVolumeChange(isExists ? priceElements.get(2).text() : "");
        openSea.setFloorPrice(isExists ? priceElements.get(3).text() : "");
        openSea.setSales(isExists ? priceElements.get(4).text() : "");
        Elements uniqueElements = element.select("span.sc-aabd2602-1");
        boolean ifExists = uniqueElements.size() == 3;
        openSea.setOwners(ifExists ? uniqueElements.get(1).text() : "");
        openSea.setItems(ifExists ? uniqueElements.get(2).text() : "");
        return openSea;
    }
}
