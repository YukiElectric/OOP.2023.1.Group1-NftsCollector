package oop.backend.crawler.crawlermethod;

import oop.backend.crawler.DataCrawler;
import oop.backend.dtos.nftexchange.NiftyGatewayTrendingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class GetNiftyGatewayTrending  {
    public ResponseEntity<?> getDataFromNiftyGateway() {
        try {
            // Phần logic chung ở đây
            List<?> data = getData();
            // Xử lý data hoặc trả về nguyên dạng tùy thuộc vào logic chung
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid selection");
        }
    }

    public abstract List<NiftyGatewayTrendingDTO> getData() throws Exception;
}
