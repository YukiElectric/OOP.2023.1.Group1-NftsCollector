package oop.frontend.jfxutils;

import javafx.concurrent.Task;
import oop.frontend.common.ApiRequest;

import java.util.List;
import java.util.Map;

public class TaskUtil extends Task<List<Map<String, String>>>{
    private final String apiUrl;
    private final String apiRequest;

    public TaskUtil(String apiUrl, String apiRequest){
        this.apiUrl = apiUrl;
        this.apiRequest = apiRequest;
    }

    @Override
    protected List<Map<String, String>> call() throws Exception {
        return ApiRequest.getRequest(apiUrl,apiRequest);
    }
}
