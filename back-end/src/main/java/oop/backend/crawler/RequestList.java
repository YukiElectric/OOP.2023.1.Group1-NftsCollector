package oop.backend.crawler;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@NoArgsConstructor
public class RequestList {
    protected final Map<String, String> selectionToRequest = new HashMap<>();
}
