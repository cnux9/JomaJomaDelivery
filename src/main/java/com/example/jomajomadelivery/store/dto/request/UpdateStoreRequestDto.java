package com.example.jomajomadelivery.store.dto.request;

import java.time.LocalTime;

public record UpdateStoreRequestDto(
        String description,
        String imgPath,
        LocalTime openTime,
        LocalTime closeTime,
        int minOrderPrice,
        int deliveryPrice
) {
}
