package org.example.Dto;

import java.util.Map;

public record ErrorResponse(
        Result result,
        String status
) {
    public record Result(
            String message,
            Map<String, String> messages
    ) {
    }
}