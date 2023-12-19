package oop.backend.crawler.hottag;

import lombok.AllArgsConstructor;
import oop.backend.App;
import oop.backend.config.PathFile;
import oop.backend.config.Url;
import oop.backend.properties.hottag.HotTagProperty;
import oop.backend.dtos.hottag.HotTagDTO;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.json.JsonUtil;
import oop.backend.utils.scroll.tag.HotTagUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RestController
@RequestMapping("${api.v1}/hottag")
public class HotTagCrawler {
    private final String PATH_HOTTAG= PathFixUtil.fix(App .class.getResource(PathFile.PATH_HOTTAG).getPath());
    private static Document document = null;

    @AllArgsConstructor
    static
    class getHTML extends Thread {
        private String search;

        @Override
        public void run() {
            LocalDate currentDate = LocalDate.now();
            String startDate = currentDate.minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endDate = currentDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String url =
                    "https://nitter.net/search?f=tweets&q=%23" + search + "&since=" + startDate + "&until=" + endDate + "&near=";
            try {
                document = HotTagUtil.getHTML(Url.URL_HOTTAG, url);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<HotTagDTO> getTag() {
        List<String> searchList = new ArrayList<>(Arrays.asList("nft", "opensea", "rarible", "binance"));
        getHTML[] threads = new getHTML[searchList.size()];
        for (int i = 0; i < searchList.size(); i++) {
            threads[i] = new getHTML(searchList.get(i));
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Elements elements = document.select("div.timeline-item");
        List<HotTagDTO> hotTags = new ArrayList<>();
        for (Element element : elements) {
            HotTagProperty.attrGet(element, hotTags);
        }
        Comparator<HotTagDTO> sort = Comparator.comparing(HotTagDTO::getPost).reversed();
        hotTags.sort(sort);
        return hotTags;
    }
    private JsonUtil<HotTagDTO> jsonHandler = new JsonUtil<>(PATH_HOTTAG);

    @GetMapping("")
    public ResponseEntity<?> getDataTag() {
        try {
            return jsonHandler.handleJsonOperation(()->getTag());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
