package oop.backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetDataWithJsoup {
    public static void main(String[] args) throws Exception {
        String url = "https://opensea.io/";
        Document document = Jsoup.connect(url)
            .userAgent("Jsoup client")   //Đây là biến môi trường thiết bị hay là môi trường truy cập web tùy web phải điều chỉnh đề vào được
            .get();
        
        Elements elements = document.select("img"); //Tìm ra tất cả các thẻ img
        
        for (Element element : elements) {
            String imgSrc = element.attr("src");  //Tìm src trong các thẻ đó
            System.out.println(imgSrc);
        }
        
        // Đây là cách lấy đơn giản nhất còn 1 cách lấy nữa nhưng khá dài và syntax khá khó nhớ đó là dùng HttpClient có thể lên mạng đọc thêm
    }
}
