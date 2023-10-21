package oop.backend;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("data") // đây là đường link dẫn đến api trả về json thường nếu ko chỉnh port thì chỉ cần truy cập https:://localhost:8080/data là xem được
public class UseGsonAndSpringBoot {
    private List<Binance> getBinaceData() throws Exception{  //Ví dụ lấy data ở trả về của trang binance ở đây data bị html mã hóa lên jsoup không lấy được nếu muôn lấy có thể dùng cách lấy GetDataIfNeedApi
        String url = "https://www.binance.com/en/nft/home";
        Document document = Jsoup.connect(url)
            .userAgent("Iphone")
            .get();
        Elements elements = document.select("div.css-vurnku");
        List<Binance> binances = new ArrayList<>();
        for (Element element : elements) {
            Binance binance = new Binance();
            binance.setImg(element.select("div.css-1x55ckl").select("img").attr("src"));
            binance.setName(element.select("div.css-1rwh7zc").text());
            Elements priceElements = element.select("div.css-9w1gf");
            boolean isExists = priceElements.size() != 0;
            binance.setVolume(isExists ? priceElements.get(0).text() : "");
            binance.setFloorPrice(isExists ? priceElements.get(1).text() : "");
            boolean isData = binance.getImg()!= null || binance.getName() != null || binance.getVolume() != null || binance.getFloorPrice() != null;
            if(isData) binances.add(binance);
        }
        return binances;
    }
    
    @GetMapping("") // sau data không tạo thêm / nào nữa
    public ResponseEntity<?> getData() throws Exception{
        Gson gson = new Gson();
        String jsonString = gson.toJson(getBinaceData());
        return ResponseEntity.ok(jsonString);  //Chạy class App sau đó ra browser chạy localhost:8080/data
    }
}
