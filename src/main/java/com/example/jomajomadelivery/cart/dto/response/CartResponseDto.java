package com.example.jomajomadelivery.cart.dto.response;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.item.entity.Item;

import java.util.Collections;
import java.util.List;

public record CartResponseDto(
        Long cartId,
        List<Item> items
) {
    public static CartResponseDto toDto(Cart cart) {
        List<Item> items = cart.getItems() == null ? Collections.emptyList() : cart.getItems();
        return new CartResponseDto(
                cart.getCartId(),
                items
        );
    }

}
