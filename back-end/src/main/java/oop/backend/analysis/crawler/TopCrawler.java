package oop.backend.analysis.crawler;

import oop.backend.App;
import oop.backend.analysis.Analyzer;
import oop.backend.analysis.correlation.DataElement;
import oop.backend.crawler.post.GetDataTwitter;
import oop.backend.dtos.post.TwitterDTO;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.jsonhandler.JsonHandlerUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Component
@RestController
@RequestMapping("${api.v1}/analysis")

/* Đào data về các post trên twitter có hashtag là tên các top nft (top 10)
   trên từng sàn nft để phục vụ cho việc phân tích */

public class TopCrawler extends Analyzer {
    private static final GetDataTwitter getDataTwitter = new GetDataTwitter();
    private static final List<String> processedNames = new ArrayList<>();
    private static int count = 1;

    /* List<DataElement> loadData() ... */

    /* handleData() */
    @Override
    public List<DataElement> handleData() throws Exception {

        List<DataElement> loadDataset = loadData();
        List<DataElement> dataset = new ArrayList<>();
        for (DataElement element : loadDataset) {
            String marketplace = element.getMarketplace();
            String nftName = element.getName();

            List<TwitterDTO> postList = new ArrayList<>();
            // Lưu vào json/post/top/{marketplace}/{nftName}.json
            String SAVE_PATH = PathFixUtil.fix(App.class.getResource("/json/post/top/").getPath() + marketplace +"/" + nftName + ".json");
            File f = new File(SAVE_PATH);
            if(f.exists()){
                System.out.println("Post data of " + nftName + " in " + marketplace + " is already existed");
                System.out.println("(" + count++ + "/40)");
            }
            else {
                JsonHandlerUtil<TwitterDTO> jsonHandler = new JsonHandlerUtil<>(SAVE_PATH);
                System.out.println("Processing " + nftName + " from " + marketplace);
                if(!processedNames.contains(nftName)){
                    processedNames.add(nftName);
                    jsonHandler.saveDataToJson(getDataTwitter.getData(nftName));
                    System.out.println(nftName + " saved to path " + SAVE_PATH);
                    System.out.println("(" + count++ + "/40)");
                }
                else {
                    System.out.println("Post data of " + nftName + " is already existed in other marketplace directory.");
                    System.out.println("(" + count++ + "/40)");
                }
            }
        }

        return null;
    }

    // Gửi response
    @GetMapping("/update-post-dataset")
    public ResponseEntity<?> response() {
        try {
            return ResponseEntity.ok(handleData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
