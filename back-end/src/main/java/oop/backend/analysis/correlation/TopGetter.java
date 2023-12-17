package oop.backend.analysis.correlation;

import oop.backend.App;
import oop.backend.analysis.Analyzer;
import oop.backend.analysis.utils.JsonReader;
import oop.backend.dtos.post.TwitterDTO;
import oop.backend.utils.fix.PathFixUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



@Component
@RestController
@RequestMapping("${api.v1}/analysis")
public class TopGetter extends Analyzer {


    /* List<DataElement> loadData() ... */

    /* handleData() */
    @Override
    public List<DataElement> handleData() {
        List<DataElement> loadDataset = loadData();
        List<DataElement> dataset = new ArrayList<>();
        for (DataElement element : loadDataset) {
            String marketplace = element.getMarketplace();
            String nftName = element.getName();
            double nftVolume = element.getVolume();
            String currency = element.getCurrency();

            System.out.println("Processing " + nftName + " from " + marketplace);
            // Lấy các post đã update từ TopCrawler trong json/post/top/{marketplace}/{nftName}.json
            String LOAD_PATH = PathFixUtil.fix(App.class.getResource("/json/post/top/").getPath() + marketplace + "/" + nftName + ".json");
            System.out.println("Loading post data of " + nftName + " in " + LOAD_PATH);
            List<TwitterDTO> postList = JsonReader.jsonToObjectList(LOAD_PATH, TwitterDTO.class);
            List<String> contentList = new ArrayList<>();
            for (TwitterDTO t : postList) {
                contentList.add(t.getContent());
            }
             dataset.add(new DataElement(marketplace, nftName, nftVolume, currency, contentList));
        }

        return dataset;
    }

    // Gửi response
    @GetMapping("/top")
    public ResponseEntity<?> response() {
        try {
            return ResponseEntity.ok(handleData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
