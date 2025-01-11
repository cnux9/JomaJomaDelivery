package com.example.jomajomadelivery.cart.dto.request;

public record AddCartRequestDto(
        Long menuId,
        Integer quantity
) {
}
