package oop.backend.crawler.abstractcrawler;

import oop.backend.dtos.eplatform.NiftyTrendingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class GetNiftyTrending {
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

    public abstract List<NiftyTrendingDTO> getData() throws Exception;
}
