package com.example.jomajomadelivery.menu.dto.response;

public record MenuResponseDto(
        Long id,
        String name,
        String description,
        int price,
        String img_path
) {
}