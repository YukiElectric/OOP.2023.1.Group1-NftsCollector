package oop.backend.attributesgetter.hottag;

import oop.backend.dtos.hottag.HotTagDTO;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GetAttrHotTag {

    public static void attrGet(Element element,List<HotTagDTO> hotTags) {
        Elements hasTags = element.select("div.tweet-content").first().select("a");
        for (Element hasTag : hasTags) {
            String tag = hasTag.text().toLowerCase();
            HotTagDTO hotTag = new HotTagDTO();
            hotTag.setHasTag(tag);
            hotTag.setFreq(1);
            AtomicBoolean isExisting = new AtomicBoolean(false);
            hotTags.forEach((item) -> {
                if (item.getHasTag().equals(tag)) {
                    item.setFreq(item.getFreq() + 1);
                    isExisting.set(true);
                }
            });
            if (!isExisting.get() && tag.startsWith("#"))
                hotTags.add(hotTag);
        }
    }
}