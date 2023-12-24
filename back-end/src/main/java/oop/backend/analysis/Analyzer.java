package oop.backend.analysis;

import oop.backend.App;
import oop.backend.analysis.dataset.PostData;
import oop.backend.analysis.utils.CurrencyHandler;
import oop.backend.analysis.utils.JsonReader;
import oop.backend.dtos.BaseDTO;
import oop.backend.utils.fix.PathFixUtil;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** Lớp trừu tượng chung cho các lớp phân tích dữ liệu **/
public abstract class Analyzer {
    
    
    // Load data từ các file json đã được lưu trữ để phục vụ cho việc phân tích
    protected List<PostData> loadData(String selection) {
        selection = PathFixUtil.fix(selection);
        List<String> pathList = new ArrayList<>();
        List<PostData> dataset = new ArrayList<>();
            // Đọc từ /json/top/{selection}.json
            System.out.println("-Loading data from " + selection);
            String readPath = PathFixUtil.fix(App.class.getResource("/json/top/" + selection + ".json").getPath());
            // Lấy ra 10 nft đứng đầu của từng sàn

            for (int i = 0; i < 10; i++) {
                List<BaseDTO> baseDTOList = JsonReader.jsonToObjectList(readPath, BaseDTO.class);
                BaseDTO nft = baseDTOList.get(i);

                String collection = nft.getCollection();

                double nftVolume = CurrencyHandler.getValue(nft.getVolume());
                String currency = CurrencyHandler.getCurrency(nft.getVolume());
                String postPath = App.class.getResource("/json/post/").getPath() +"top/"+ selection +"/" + collection + ".json";
                File f = new File(postPath);
                if(f.exists()){
                    dataset.add(new PostData(selection, collection, nftVolume, currency, new ArrayList<>()));
                    pathList.add(PathFixUtil.fix(postPath.toString()));
                    System.out.println("Loading post data of " + collection + " in " + PathFixUtil.fix(App.class.getResource("/json/post/top/").getPath() + selection +"/" + collection + ".json"));
                } else {
                    dataset.add(new PostData(selection, collection, nftVolume, "", new ArrayList<>()));
                    pathList.add(PathFixUtil.fix(postPath.toString()));
                    System.out.println("Post data of " + collection + " in " + selection + " is not available");
                }

            }
        return dataset;
    }

    // Các phương thức phân tích data
    public abstract List<?> handleData(String selection) throws Exception;

    // Gửi response
    public abstract ResponseEntity<?> response(String selection);
}
