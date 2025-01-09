package com.example.jomajomadelivery.menu.dto.request;

public record MenuRequestDto(
        String name,
        String description,
        int price,
        String img_path
) {
}
