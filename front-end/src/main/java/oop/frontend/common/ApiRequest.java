/**
 * Lớp ApiRequest cung cấp các phương thức để thực hiện các yêu cầu HTTP GET
 * và lấy dữ liệu từ địa chỉ API được chỉ định.
 */
package oop.frontend.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ApiRequest {

    /**
     * Gửi một yêu cầu HTTP GET đến địa chỉ apiURL
     *
     * @param apiUrl    URL đã cung cấp.
     * @param request   Yêu cầu cụ thể được nối thêm vào URL cơ sở GET.
     * @return          Danh sách các bản đồ chứa dữ liệu được lấy từ phản hồi của API.
     * @throws Exception nếu có vấn đề xảy ra trong quá trình xử lý yêu cầu hoặc phản hồi.
     */
    public static List<Map<String,String>> getRequest(String apiUrl, String request) throws Exception{
        String urlWithParams = request.isEmpty() ? apiUrl : apiUrl+"/"+request;
        URL url = new URL(urlWithParams);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))){
            StringBuilder response = new StringBuilder();
            String data;
            while((data = reader.readLine())!=null) response.append(data);
            Type type = new TypeToken<List<Map<String,String>>>() {}.getType();
            return new Gson().fromJson(response.toString(), type);
        }finally {
            httpURLConnection.disconnect();
        }
    }
}
