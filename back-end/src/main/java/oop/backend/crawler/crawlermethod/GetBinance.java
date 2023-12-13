package oop.backend.crawler.crawlermethod;

import oop.backend.crawler.DataCrawler;
import oop.backend.crawler.RequestList;
import oop.backend.dtos.nftexchange.BinanceDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class GetBinance extends RequestList implements DataCrawler {

    // Các thuộc tính chung, nếu có

    public ResponseEntity<?> getDataFromBinance(String selection) {
        try {
            // Phần logic chung ở đây
            List<?> data = getData(selection);
            // Xử lý data hoặc trả về nguyên dạng tùy thuộc vào logic chung
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }

    // Phương thức trừu tượng để lấy dữ liệu cụ thể từ Binance
    public abstract List<BinanceDTO> getData(String selection) throws Exception;
}
