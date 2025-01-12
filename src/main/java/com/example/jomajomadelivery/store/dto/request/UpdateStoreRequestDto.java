package com.example.jomajomadelivery.store.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

public record UpdateStoreRequestDto(
        String description,
        MultipartFile img,
        LocalTime openTime,
        LocalTime closeTime,
        int minOrderPrice,
        int deliveryPrice
) {
}
