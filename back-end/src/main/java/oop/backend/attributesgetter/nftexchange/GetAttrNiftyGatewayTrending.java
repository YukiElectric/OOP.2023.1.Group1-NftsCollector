package oop.backend.attributesgetter.nftexchange;

import oop.backend.attributesgetter.AttrGetter;
import oop.backend.dtos.nftexchange.NiftyGatewayTrendingDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAttrNiftyGatewayTrending implements AttrGetter<NiftyGatewayTrendingDTO> {
    @Override
    public NiftyGatewayTrendingDTO attrGet(Element element) {
        NiftyGatewayTrendingDTO niftyGateway = new NiftyGatewayTrendingDTO();
        niftyGateway.setName(element.select("p.css-1r0io98").text());
        Elements niftyElements = element.select("p.css-1sxsghr");
        boolean isExist = niftyElements.size() == 2;
        if (!niftyElements.isEmpty()) {
            niftyGateway.setCreator(isExist ? niftyElements.get(0).text() : "");
            niftyGateway.setEdition(isExist ? niftyElements.get(1).text() : "");
        }
        niftyGateway.setLikes(element.select("span.jss2341").text());
        niftyGateway.setFloorPrice(element.select("p.css-1fiixjq").select("span").first().text());
        if(!niftyGateway.getName().isEmpty()) return niftyGateway;
        return null;
    }
}
