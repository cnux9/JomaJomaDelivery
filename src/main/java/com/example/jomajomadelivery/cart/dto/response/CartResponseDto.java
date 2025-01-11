package com.example.jomajomadelivery.cart.dto.response;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.item.entity.Item;

import java.util.List;

public record CartResponseDto(
        Long cartId,
        List<Item> items
) {
    public static CartResponseDto toDto(Cart cart) {
        return new CartResponseDto(
                cart.getCartId(),
                // TODO: items 직렬화 가능?
                cart.getItems()
        );
    }
}
