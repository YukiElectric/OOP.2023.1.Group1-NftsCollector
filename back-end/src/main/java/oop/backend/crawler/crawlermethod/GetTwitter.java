package oop.backend.crawler.crawlermethod;

import oop.backend.crawler.DataCrawler;
import oop.backend.crawler.RequestList;
import oop.backend.dtos.post.TwitterDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class GetTwitter implements DataCrawler {
    public ResponseEntity<?> getDataFromTwitter(String selection) {
        try {
            // Phần logic chung ở đây
            List<?> data = getData(selection);
            // Xử lý data hoặc trả về nguyên dạng tùy thuộc vào logic chung
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }

    public abstract List<TwitterDTO> getData(String selection) throws Exception;
}
