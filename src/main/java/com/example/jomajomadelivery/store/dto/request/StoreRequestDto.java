package com.example.jomajomadelivery.store.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
public record StoreRequestDto (
    String category,
    @NotBlank
    String name,
     String description,
    @NotBlank
     String address,
    @NotBlank
     String phoneNumber,
     String imgPath,
     LocalTime openTime,
     LocalTime closeTime,
     Integer minOrderPrice,
     Integer deliveryPrice
){}
