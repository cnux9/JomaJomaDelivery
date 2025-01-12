package com.example.jomajomadelivery.store.dto.response;

import com.example.jomajomadelivery.menu.dto.response.MenuResponseDto;
import com.example.jomajomadelivery.store.entity.Category;
import com.example.jomajomadelivery.store.entity.Store;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public record StoreAndMenusResponseDto(
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
        Integer minOrderPrice,
        Integer deliveryPrice,
        Long favoriteCount,
        List<MenuResponseDto> menus
) {
    public static StoreAndMenusResponseDto toDTO(Store store,String address) {
        return new StoreAndMenusResponseDto(
                store.getStoreId(),
                store.getCategory(),
                store.getName(),
                store.getDescription(),
                address,
                store.getPhoneNumber(),
                store.getImgPath(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getRating(),
                store.getMinOrderPrice(),
                store.getDeliveryPrice(),
                store.getFavoriteCount(),
                // 메뉴 데이터를 MenuResponseDto로 매핑
                store.getMenus().stream()
                        .map(MenuResponseDto::toDto)
                        .collect(Collectors.toList())
        );
    }
}
