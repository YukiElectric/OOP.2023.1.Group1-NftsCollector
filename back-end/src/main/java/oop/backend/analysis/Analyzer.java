package oop.backend.analysis;

import oop.backend.App;
import oop.backend.analysis.correlation.DataElement;
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
    private static final List<String> marketplaces = List.of("binance", "nifty_gateway", "open_sea", "rarible");
    
    
    // Load data từ các file json đã được lưu trữ để phục vụ cho việc phân tích
    protected List<DataElement> loadData() {
        List<String> pathList = new ArrayList<>();
        List<DataElement> dataset = new ArrayList<>();
        for (String marketplace : marketplaces){
            // Đọc từ /json/top/{marketplace}_data.json
            System.out.println("Loading data from " + marketplace);
            String readPath = PathFixUtil.fix(App.class.getResource("/json/top/" + marketplace + "_data.json").getPath());
            // Lấy ra 10 nft đứng đầu của từng sàn
            for (int i = 0; i < 10; i++) {
                List<BaseDTO> baseDTOList = JsonReader.jsonToObjectList(readPath, BaseDTO.class);
                BaseDTO nft = baseDTOList.get(i);

                String nftName = nft.getName();

                double nftVolume = CurrencyHandler.getValue(nft.getVolume());
                String currency = CurrencyHandler.getCurrency(nft.getVolume());
                String postPath = App.class.getResource("/json/post/top/").getPath() + marketplace +"/" + nftName + ".json";
                File f = new File(postPath);
                if(f.exists()){
                    dataset.add(new DataElement(marketplace, nftName, nftVolume, currency, new ArrayList<>()));
                    pathList.add(PathFixUtil.fix(postPath.toString()));
                    System.out.println("Loading post data of " + nftName + " in " + PathFixUtil.fix(App.class.getResource("/json/post/top/").getPath() + marketplace +"/" + nftName + ".json"));
                } else {
                    dataset.add(new DataElement(marketplace, nftName, nftVolume, "", new ArrayList<>()));
                    pathList.add(PathFixUtil.fix(postPath.toString()));
                    System.out.println("Post data of " + nftName + " in " + marketplace + " is not available");
                }

            }
        }
        return dataset;
    }

    // Các phương thức phân tích data
    public abstract List<?> handleData() throws Exception;

    // Gửi response
    public ResponseEntity<?> response() {
        try {
            return ResponseEntity.ok(handleData());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
