package oop.backend.analysis.relation;

import oop.backend.App;
import oop.backend.analysis.Analyzer;
import oop.backend.analysis.dataset.PostData;
import oop.backend.analysis.utils.JsonReader;
import oop.backend.dtos.post.TwitterDTO;
import oop.backend.utils.fix.PathFixUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Component
@RestController
@RequestMapping("${api.v1}/analysis")
public class ChartDatasetMaker extends Analyzer {


    /* List<DataElement> loadData() ... */

    /* handleData() */
    @Override
    public List<ChartDataElement> handleData(String selection) {
        List<PostData> loadDataset = loadData(selection);
        List<ChartDataElement> dataset = new ArrayList<>();

        for (PostData element : loadDataset) {
            String marketplace = element.getMarketplace();
            String collection = element.getCollection();
            double volume = element.getVolume();
            String currency = element.getCurrency();

            System.out.println("Processing " + collection + " from " + marketplace);
            // Lấy các post đã update từ TopCrawler trong json/post/top/{marketplace}/{collection}.json
            String LOAD_PATH = PathFixUtil.fix(App.class.getResource("/json/post/").getPath() + "top/" + marketplace + "/" + collection + ".json");
            System.out.println("Loading post data of " + collection + " in " + LOAD_PATH);
            List<TwitterDTO> postList = JsonReader.jsonToObjectList(LOAD_PATH, TwitterDTO.class);
            int numberOfPost = postList.size();
            dataset.add(new ChartDataElement(marketplace, collection, volume, currency, numberOfPost));

        }
        return dataset;
    }

    // Gửi response
    @Override
    @GetMapping("/top/{selection}")
    public ResponseEntity<?> response(@PathVariable String selection) {
        try {
            List<ChartDataElement> dataset = handleData(selection);
            return ResponseEntity.ok(dataset);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
