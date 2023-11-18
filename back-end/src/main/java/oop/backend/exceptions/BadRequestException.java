package oop.backend.exceptions;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class BadRequestException extends RuntimeException{
    private final String message;
    public BadRequestException(String message){
        super(message);
        this.message = message;
    }
    public String toJson(){
        Map<String , Object> response = new HashMap<>();
        response.put("status","fail");
        response.put("message",message);
        return new Gson().toJson(response);
    };
}
