package oop.backend.storage;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONDataStorage {
    private final String filePath;

    public JSONDataStorage(String fileName) {
        this.filePath = "D:/Document/" + fileName + ".json";
    }

    public void storeDataInJsonFile(List<?> objects) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(objects);
        FileWriter writer = new FileWriter(filePath);
        writer.write(json);
        writer.close();
    }
}
