package oop.backend.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
/** Tiện ích dùng để xử lý data đã lấy được và lưu lại sau đó xuất lên localhost
*/

public class JsonHandlerUtil<T> {
    private String filePath;
    public JsonHandlerUtil(String filePath) {
        this.filePath = filePath;
    }
    
    public ResponseEntity<?> handleJsonOperation(JsonOperation<T> operation) {
        try {
            List<T> data = operation.performOperation();
            saveDataToJson(data);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return handleErrorResponse();
        }
    }
    
    /**
     * Phương thức này dùng để lưu json
     * @param data json được xuất ra sẽ lưu ở đây
     */
    private void saveDataToJson(List<T> data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi khi ghi vào tệp
        }
    }
    
    /**
     * Phương thức xử lý khi hiển thị data bị lỗi
     */
    private ResponseEntity<?> handleErrorResponse() {
        try {
            List<T> savedData = loadDataFromJson();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(savedData);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            // Xử lý lỗi khi đọc tệp JSON
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error handling failed");
        }
    }
    
    /**
     * Phương thức trả json
     * @return : trả về giá trị json đã lưu trong phần cứng
     * @throws IOException
     */
    private List<T> loadDataFromJson() throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            Type type = new TypeToken<List<T>>() {}.getType();
            return gson.fromJson(reader, type);
        }
    }
    
    /**
     * Giao diện chức năng này định nghĩa một phương thức performOperation
     * mà bất kỳ lớp hoặc biểu thức lambda nào được chuyển vào handleJsonOperation cũng cần triển khai.
     */
    @FunctionalInterface
    public interface JsonOperation<T> {
        List<T> performOperation() throws Exception;
    }
}

