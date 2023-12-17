package oop.backend.properties.eplatform;

import oop.backend.properties.PropertyGetter;
import oop.backend.dtos.eplatform.NiftyTrendingDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NiftyTrendingProperty implements PropertyGetter<NiftyTrendingDTO> {
    @Override
    public NiftyTrendingDTO attrGet(Element element) {
        NiftyTrendingDTO niftyGateway = new NiftyTrendingDTO();
        niftyGateway.setName(element.select("p.css-1r0io98").text());
        Elements niftyElements = element.select("p.css-1sxsghr");
        boolean isExist = niftyElements.size() == 2;
        if (!niftyElements.isEmpty()) {
            niftyGateway.setCreator(isExist ? niftyElements.get(0).text() : "");
            niftyGateway.setEdition(isExist ? niftyElements.get(1).text() : "");
        }
        niftyGateway.setLikes(element.select("span[data-testid='like-count']").text());
        Element priceElement = element.select("p.css-1fiixjq").select("span").first();
        niftyGateway.setFloorPrice(priceElement == null ? "__" : priceElement.text());
        if(!niftyGateway.getName().isEmpty()) return niftyGateway;
        return null;
    }
}
