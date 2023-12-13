package oop.backend.crawler.crawlermethod;

import oop.backend.crawler.DataCrawler;
import oop.backend.crawler.RequestList;
import oop.backend.dtos.nftexchange.RaribleDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class GetRarible extends RequestList implements DataCrawler {
    public ResponseEntity<?> getDataFromRarible(String selection) {
        try {
            // Phần logic chung ở đây
            List<?> data = getData(selection);
            // Xử lý data hoặc trả về nguyên dạng tùy thuộc vào logic chung
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }

    public abstract List<RaribleDTO> getData(String selection) throws Exception;
}
