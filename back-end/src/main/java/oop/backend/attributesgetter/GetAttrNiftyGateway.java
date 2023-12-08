package oop.backend.attributesgetter;

import oop.backend.dtos.NiftyGatewayDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAttrNiftyGateway implements AttrGetter<NiftyGatewayDTO> {
    @Override
    public NiftyGatewayDTO attrGet(Element element) {
        NiftyGatewayDTO niftyGateway = new NiftyGatewayDTO();
        niftyGateway.setImg(element.select("img").attr("src"));
        niftyGateway.setName(element.select("td.css-1fgjn7z").text());
        Elements niftyElements = element.select("b");
        boolean isExist = niftyElements.size() == 5;
        if (!niftyElements.isEmpty()) {
            niftyGateway.setVolume(isExist ? niftyElements.get(2).text() : "");
            niftyGateway.setFloorPrice(isExist ? niftyElements.get(3).text() : "");
        }
        if (!niftyGateway.getFloorPrice().isEmpty() && !niftyGateway.getVolume().isEmpty())
            return niftyGateway;
        return null;
    }
}
