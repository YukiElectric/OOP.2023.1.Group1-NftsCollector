package oop.backend.analysis.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/** Đọc từ Json sang object **/
public class JsonReader {

    public static <T> List<T> jsonToObjectList(String filePath, Class<T> elementType) {
        List<T> objectList = new ArrayList<>();
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            Type type = TypeToken.getParameterized(List.class, elementType).getType();
            objectList = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objectList;
    }
}
