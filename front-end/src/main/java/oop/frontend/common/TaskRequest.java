package oop.frontend.common;

import javafx.concurrent.Task;

import java.util.List;
import java.util.Map;

public class TaskRequest extends Task<List<Map<String, String>>>{
    private final String apiUrl;
    private final String apiRequest;

    public TaskRequest(String apiUrl, String apiRequest){
        this.apiUrl = apiUrl;
        this.apiRequest = apiRequest;
    }

    @Override
    protected List<Map<String, String>> call() throws Exception {
        return ApiRequest.getRequest(apiUrl,apiRequest);
    }
}
