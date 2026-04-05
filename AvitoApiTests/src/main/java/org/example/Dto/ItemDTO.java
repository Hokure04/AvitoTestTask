package org.example.Dto;

public record ItemDTO(
        String id,
        Integer sellerId,
        String name,
        Integer price,
        StatisticsDTO statistics,
        String createdAt

) {
}
