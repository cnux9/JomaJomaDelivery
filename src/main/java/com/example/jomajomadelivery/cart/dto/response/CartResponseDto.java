package com.example.jomajomadelivery.cart.dto.response;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.item.entity.Item;

import java.util.Collections;
import java.util.List;

public record CartResponseDto(
        Long cartId,
        List<Item> items,
        int totalItemPrice,
        int totalQuantity,
        int deliveryFee,
        int orderFee,
        Long storeId
) {
    public static CartResponseDto toDto(Cart cart) {
        List<Item> items = cart.getItems() == null ? Collections.emptyList() : cart.getItems();

        // 총 아이템 가격 계산
        int totalItemPrice = items.stream()
                .mapToInt(Item::getTotalPrice)
                .sum();

        // 총 수량 계산
        int totalQuantity = items.stream()
                .mapToInt(Item::getQuantity)
                .sum();

        // 배달비 설정 (아이템이 없으면 0)
        int deliveryFee = items.isEmpty() ? 0 : items.get(0).getMenu().getStore().getDeliveryPrice();

        // 총 주문비 계산
        int orderFee = totalItemPrice + deliveryFee;

        Long storeId =items.isEmpty() ? 0 : items.get(0).getMenu().getStore().getStoreId();

        return new CartResponseDto(
                cart.getCartId(),
                items,
                totalItemPrice,
                totalQuantity,
                deliveryFee,
                orderFee,
                storeId
        );
    }
}
