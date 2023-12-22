package oop.frontend.common;

import javafx.concurrent.Task;

import java.util.List;
import java.util.Map;

public class TaskRequest extends Task<List<Map<String, String>>> {
    private final String apiUrl;
    private final String apiRequest;

    /**
     * Khởi tạo một TaskRequest với apiUrl
     *
     * @param apiUrl
     * @param apiRequest  Yêu cầu cụ thể được nối thêm vào URL cơ sở.
     */
    public TaskRequest(String apiUrl, String apiRequest) {
        this.apiUrl = apiUrl;
        this.apiRequest = apiRequest;
    }

    /**
     * Thực hiện nhiệm vụ để lấy dữ liệu từ API sử dụng ApiRequest.
     *
     * @return  dữ liệu được lấy từ phản hồi của API.
     * @throws Exception nếu có vấn đề xảy ra trong quá trình xử lý yêu cầu hoặc phản hồi.
     */
    @Override
    protected List<Map<String, String>> call() throws Exception {
        return ApiRequest.getRequest(apiUrl, apiRequest);
    }
}
