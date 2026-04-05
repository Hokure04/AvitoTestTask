package org.example.Dto;

public record ItemRequest(
        Integer sellerId,
        String name,
        Integer price,
        StatisticsDTO statistics
) {
}
