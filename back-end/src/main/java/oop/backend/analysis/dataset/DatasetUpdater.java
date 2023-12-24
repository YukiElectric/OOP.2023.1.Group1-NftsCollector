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

import java.io.File;
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
    private static Map<String, List<TwitterDTO>> dataset = null;


    /* List<DataElement> loadData() ... */

    /* handleData() */
    @Override
    public List<PostData> handleData(String selection) throws Exception {
        int count = 1;
        List<PostData> loadDataset = loadData(selection);
        List<PostData> dataset = new ArrayList<>();
        for (PostData element : loadDataset) {
            String marketplace = element.getMarketplace();
            String collection = element.getCollection();

            List<TwitterDTO> postList = new ArrayList<>();
            // Lưu vào json/post/top/{marketplace}/{collection}.json
            String SAVE_PATH = PathFixUtil.fix(App.class.getResource("/json/post/").getPath() + "top/" + marketplace + "/" + collection + ".json");
            File f = new File(SAVE_PATH);
            if (f.exists()) {
                System.out.println("Post data of " + collection + " in " + marketplace + " is already existed");
                System.out.println("(" + count++ + "/40)");
            } else {
                JsonUtil<TwitterDTO> jsonHandler = new JsonUtil<>(SAVE_PATH);
                System.out.println("Processing " + collection + " from " + marketplace);

                jsonHandler.handleJsonOperation(() -> twitterCrawler.getData(collection));
                System.out.println(collection + " saved to path " + SAVE_PATH);
                System.out.println("(" + count++ + "/10)");
            }
        }

        return null;
    }

    // Gửi response
    @Override
    @GetMapping("/update-post-dataset/{selection}/AllTime")
    public ResponseEntity<?> response(@PathVariable String selection) {
        try {
            return ResponseEntity.ok(handleData(selection));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }
}