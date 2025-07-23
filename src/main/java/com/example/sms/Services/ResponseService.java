package com.example.sms.Services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseService {
    public static Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    public static Map<String, Object> createSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        response.put("data", data);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}
