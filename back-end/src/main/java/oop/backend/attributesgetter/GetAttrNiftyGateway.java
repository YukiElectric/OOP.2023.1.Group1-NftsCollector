package oop.backend.attributesgetter;

import oop.backend.dtos.NiftyGatewayDTO;
import org.jsoup.nodes.Element;

public class GetAttrNiftyGateway implements AttrGetter<NiftyGatewayDTO> {
    @Override
    public NiftyGatewayDTO attrGet(Element element) {
        NiftyGatewayDTO niftyGateway = new NiftyGatewayDTO();
        niftyGateway.setImg(element.select("img").first().attr("src"));
        niftyGateway.setName(element.select("p.css-1q0cfzn").text());
        niftyGateway.setFloor_price(element.select("p.css-81s8ad").text());
        if(!niftyGateway.getFloor_price().isEmpty()) return niftyGateway ;
        return null;
    }
}
