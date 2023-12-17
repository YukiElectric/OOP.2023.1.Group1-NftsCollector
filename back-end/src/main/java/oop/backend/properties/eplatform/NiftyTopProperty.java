package oop.backend.properties.eplatform;

import oop.backend.properties.PropertyGetter;
import oop.backend.dtos.eplatform.NiftyTopDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NiftyTopProperty implements PropertyGetter<NiftyTopDTO> {
    @Override
    public NiftyTopDTO attrGet(Element element) {
        NiftyTopDTO niftyGateway = new NiftyTopDTO();
        niftyGateway.setName(element.select("td.css-brcvnd").text());
        if(niftyGateway.getName().isEmpty()) return null;
        Elements niftyElements = element.select("td.css-lvk4hs");
        boolean isExist = niftyElements.size() == 8;
        if (!niftyElements.isEmpty()) {
            niftyGateway.setVolume(isExist ? niftyElements.get(0).text().split(" ")[0] : "");
            niftyGateway.setFloorPrice(isExist ? niftyElements.get(2).text() : "");
            niftyGateway.setItems(isExist ? niftyElements.get(4).text() : "");
            niftyGateway.setOwners(isExist ? niftyElements.get(5).text() : "");
        }
        if (!niftyGateway.getFloorPrice().isEmpty() && !niftyGateway.getVolume().isEmpty())
            return niftyGateway;
        return null;
    }
}
