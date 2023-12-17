package oop.backend.analysis.positivity;

import com.google.gson.Gson;
import lombok.Getter;
import oop.backend.analysis.Analyzer;
import oop.backend.analysis.correlation.DataElement;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


/** Gửi request lên chatgpt để đánh giá độ tích cực của bài viết **/
@Component
@RestController
@RequestMapping("${api.v1}/analysis")
@Getter
public class GPTRequest extends Analyzer{


    private static final String API ="https://api.openai.com/v1/chat/completions";

    private static final String API_KEY = "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";

    private static final String API_KEY_2 = "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";

    private static final String REQUEST_BODY = """
            { 
                "model": "gpt-3.5-turbo", 
                "max_tokens": 50, 
                "messages" : [{"role":"user", "content":"Label sentiment(index+only positive|neutral|negative): %s"}]
            }
            """;

    /* List<DataElement> loadData() ... */

    /* handleData() */
    @Override
    public List<?> handleData() throws Exception {
         //testing
//        List<DataElement> loadDataset = loadPath();
        List<DataElement> test = new ArrayList<>();
        test.add(new DataElement("test", "test", 0, "",
                List.of("Lets buy this #NFT", "This is suck", "Today's nft price")));
        List<String> response = new ArrayList<>();
        response.add(getGPTResponse(test));
        return response;
    }

    // Gửi resonse
    @GetMapping("/test/positivity")
    public ResponseEntity<?> response() {
        try {
            return ResponseEntity.ok(handleData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }




    public String getGPTResponse (List<DataElement> dataset) throws Exception {
        System.out.println("key"+API_KEY);
        List<String> contentList = dataset.get(0).getContentList();
        System.out.println(contentList);
        String res = null;

        StringBuilder inputBuilder = new StringBuilder();
        int i = 0;
        int size = contentList.size();
        int toggle = 0;
        for (String s : contentList) {
            i++;
            inputBuilder.append("(").append(i).append(".)").append(s);
            System.out.println(inputBuilder.toString());
            if (i == 10 || i == size) {
                try {
                    System.out.println("1");
                    String input = String.format(REQUEST_BODY, inputBuilder.toString());
                    System.out.println(input);
                    Gson gson = new Gson();
                    String key = (toggle == 0) ? API_KEY : API_KEY_2;
                    System.out.println("toggle "+toggle);
                    toggle = 1-toggle;
                    String restring = sendRequest(API, input, key);

                    System.out.println(restring);
                    ResponseDTO response = gson.fromJson(restring , ResponseDTO.class);
                    System.out.println(response.getChoices().get(0).getMessage().getContent());
                    res = response.getChoices().get(0).getMessage().getContent();

                    i=0;
                    inputBuilder.setLength(0);
                    Thread.sleep(3000);
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
