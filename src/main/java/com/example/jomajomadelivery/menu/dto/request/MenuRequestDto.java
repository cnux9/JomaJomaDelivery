package com.example.jomajomadelivery.menu.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record MenuRequestDto(
        String name,
        String description,
        int price,
        MultipartFile img
) {
}
