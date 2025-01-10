package com.example.jomajomadelivery.item.dto.response;

import com.example.jomajomadelivery.item.entity.Item;
import lombok.Builder;

@Builder
public record ItemResponseDto(
        Long itemId,
        Long cartId,
        Long menuId,
        String name,
        int quantity,
        int price,
        int totalPrice

) {
    public static ItemResponseDto toDto(Item item){
        return ItemResponseDto.builder()
                .itemId(item.getItemId())
                .cartId(item.getCart().getCartId())
                .menuId(item.getMenu().getMenu_id())
                .name(item.getName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .totalPrice(item.getTotalPrice())
                .build();
    }
}
