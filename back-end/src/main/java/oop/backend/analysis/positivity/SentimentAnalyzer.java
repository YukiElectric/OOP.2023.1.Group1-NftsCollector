package oop.backend.analysis.positivity;

import com.google.gson.Gson;
import lombok.Getter;
import oop.backend.App;
import oop.backend.analysis.Analyzer;
import oop.backend.analysis.dataset.PostData;
import oop.backend.analysis.utils.JsonReader;
import oop.backend.analysis.utils.ContentFilter;
import oop.backend.config.Openai;
import oop.backend.config.PathFile;
import oop.backend.dtos.BaseDTO;
import oop.backend.dtos.post.TwitterDTO;
import oop.backend.utils.fix.PathFixUtil;
import oop.backend.utils.json.JsonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/** Gửi request lên chatgpt để đánh giá độ tích cực của bài viết **/
@Component
@RestController
@RequestMapping("${api.v1}/analysis")
@Getter
public class SentimentAnalyzer extends Analyzer{

    private static final String REQUEST_BODY = """
            { 
                "model": "gpt-3.5-turbo", 
                "max_tokens": 50, 
                "messages" : [{"role":"user", "content":"Label sentiment(only positive|neutral|negative foreach content): %s"}]
            }
            """;

    /* List<DataElement> loadData() ... */

    /* handleData() */
    @Override
    public List<PositivityData> handleData(String selection) throws Exception {
        System.out.println("GPTRequest");
        List<PostData> loadDataset = loadData(selection);
        List<PositivityData> res = new ArrayList<>();
        for (PostData element : loadDataset) {
            String marketplace = element.getMarketplace();
            String collection = element.getCollection();
            System.out.println("Processing " + collection + " from " + marketplace);
            String LOAD_PATH = PathFixUtil.fix(App.class.getResource("/json/post/").getPath() + "top/" + marketplace + "/" + collection + ".json");
            System.out.println("Loading post data of " + collection + " in " + LOAD_PATH);
            List<TwitterDTO> postList = JsonReader.jsonToObjectList(LOAD_PATH, TwitterDTO.class);
            List<String> contentList = new ArrayList<>();
            for (TwitterDTO t : postList) {
                contentList.add(ContentFilter.removeSpecialCharacters(t.getContent()));
            }
            PositivityData positivity = new PositivityData(collection, 0,0,0);
            List<String> response = getGPTResponse(contentList);
            for (String r : response) {
                PositivityData p = ContentFilter.countRating(collection, r);
                positivity.setPositive(positivity.getPositive() + p.getPositive());
                positivity.setNeutral(positivity.getNeutral() + p.getNeutral());
                positivity.setNegative(positivity.getNegative() + p.getNegative());
            }
            res.add(positivity);
        }
        return res;
    }



    // Gửi resonse
    @GetMapping("/positivity/{selection}")
    public ResponseEntity<?> response(@PathVariable String selection) {
        try {
            String SAVE_PATH = PathFixUtil.fix(App.class.getResource(PathFile.PATH_POSITIVITY).getPath()) + selection + ".json";
            JsonUtil<PositivityData> jsonHandler = new JsonUtil<>(SAVE_PATH);
            return jsonHandler.handleJsonOperation(() -> handleData(selection));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }

    private List<String> marketplaceList = new ArrayList<>(Arrays.asList("open_sea", "rarible", "binance", "nifty_gateway"));

    @GetMapping("/positivity/all")
    public ResponseEntity<?> responseAll() {
        try {
            List<PositivityData> list = new ArrayList<>();
            for (String marketplace : marketplaceList) {
                String LOAD_PATH = PathFixUtil.fix(App.class.getResource(PathFile.PATH_POSITIVITY).getPath()) + "binance.json";
                for(PositivityData o : JsonReader.jsonToObjectList(LOAD_PATH, PositivityData.class)){
                    list.add(o);
                }
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }


    private static final String[] API_KEYS = {Openai.API_KEY_1, Openai.API_KEY_2, Openai.API_KEY_3, Openai.API_KEY_4};

    public List<String> getGPTResponse(List<String> contentList) throws Exception {
        List<String> res = new ArrayList<>();

        StringBuilder inputBuilder = new StringBuilder();
        int i = 0;
        int size = contentList.size();

        for (String s : contentList) {
            i++;
            inputBuilder.append("(").append(i).append(".)").append(s);
            int toggle = 0;
            if (i == 10 || i == size) {
                try {
                    String input = String.format(REQUEST_BODY, inputBuilder.toString());
                    String key = API_KEYS[toggle];
                    toggle = (toggle + 1) % API_KEYS.length;

                    String restring = sendRequest(Openai.API, input, key);
                    Gson gson = new Gson();
                    ResponseGPT response = gson.fromJson(restring, ResponseGPT.class);
                    res.add(response.getChoices().get(0).getMessage().getContent());

                    i = 0;
                    size -= 10;
                    inputBuilder.setLength(0);
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }



    private static String sendRequest(String url, String data, String key) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + key)
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
