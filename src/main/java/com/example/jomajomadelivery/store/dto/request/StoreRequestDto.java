package com.example.jomajomadelivery.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
@Getter
@AllArgsConstructor
public class StoreRequestDto {
    private String category;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String address;
    @NotBlank
    private String phoneNumber;
    private String imgPath;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int minOrderPrice;
    private int deliveryPrice;
}
