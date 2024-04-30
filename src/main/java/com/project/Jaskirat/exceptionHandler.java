package com.project.Jaskirat;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public class exceptionHandler
{
    public static ResponseEntity<Object> errorResponse(String message) {
        Map<String, String> error = Map.of("Error", message);
        return ResponseEntity.badRequest().body(error);

    }



}
