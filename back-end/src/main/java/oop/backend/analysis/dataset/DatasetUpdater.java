package oop.backend.analysis.dataset;

import lombok.AllArgsConstructor;
import oop.backend.App;
import oop.backend.analysis.Analyzer;
import oop.backend.crawler.post.TwitterCrawler;
import oop.backend.dtos.post.TwitterDTO;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.json.JsonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;


@Component
@RestController
@RequestMapping("${api.v1}/analysis")

/* Thu thập data về các post trên twitter có hashtag là tên các top nft (top 10)
   trên từng sàn nft để phục vụ cho việc phân tích */

public class DatasetUpdater extends Analyzer {
    private static final TwitterCrawler twitterCrawler = new TwitterCrawler();
    private String SAVE_PATH = PathFixUtil.fix(App.class.getResource("/json/analysis/dataset/").getPath());
    private static Map<String, List<TwitterDTO>> dataset = null;

    @AllArgsConstructor
    public class getTwitter extends Thread {
        private String collection;

        @Override
        public void run() {
            try {
                List<TwitterDTO> data = twitterCrawler.getData(collection);
                dataset.put(collection, data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* List<DataElement> loadData() ... */

    /* handleData() */
    @Override
    public List<Map<String, List<TwitterDTO>>> handleData(String selection) throws Exception {
        List<PostData> loadDataset = loadData(selection);

        for (PostData o : loadDataset){
            List<TwitterDTO> postList = twitterCrawler.getData(o.getCollection());
            dataset.put(o.getCollection(), postList);
        }

        List<Map<String, List<TwitterDTO>>> res = new ArrayList<>();
        res.add(dataset);
        return res;
    }


    // Gửi response
    @Override
    @GetMapping("/update-post-dataset/{selection}/AllTime")
    public ResponseEntity<?> response(@PathVariable String selection) {
        try {
            JsonUtil<Map<String, List<TwitterDTO>>> jsonHandler = new JsonUtil<>(SAVE_PATH + selection + ".json");
            return jsonHandler.handleJsonOperation(()-> handleData(selection));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
