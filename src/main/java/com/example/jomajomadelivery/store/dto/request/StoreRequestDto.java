package com.example.jomajomadelivery.store.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

public record StoreRequestDto(
        String category,
        String name,
        String description,
        String fullAddress,
        String zipcode,
        String state,
        String city,
        String street,
        String detailAddress,
        String phoneNumber,
        MultipartFile img,
        LocalTime openTime,
        LocalTime closeTime,
        Integer minOrderPrice,
        Integer deliveryPrice
) {
}
