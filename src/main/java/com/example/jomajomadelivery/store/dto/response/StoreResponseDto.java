package com.example.jomajomadelivery.store.dto.response;

import com.example.jomajomadelivery.store.entity.Category;
import com.example.jomajomadelivery.store.entity.Store;
import lombok.Builder;

import java.time.LocalTime;
@Builder
public record StoreResponseDto(
        Long storeId,
        Category category,
        String name,
        String description,
        String address,
        String phoneNumber,
        String imgPath,
        LocalTime openTime,
        LocalTime closeTime,
        Double rating,
        int minOrderPrice,
        int deliveryPrice,
        Long favoriteCount
) {
    public static StoreResponseDto toDTO(Store store) {
        return StoreResponseDto.builder()
                .storeId(store.getStoreId())
                .category(store.getCategory())
                .name(store.getName())
                .description(store.getDescription())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .imgPath(store.getImgPath())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .rating(store.getRating())
                .minOrderPrice(store.getMinOrderPrice())
                .deliveryPrice(store.getDeliveryPrice())
                .favoriteCount(store.getFavoriteCount())
                .build();
    }
}