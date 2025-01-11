package com.example.jomajomadelivery.orders.dto.response;

import com.example.jomajomadelivery.orders.entity.Order;
import com.example.jomajomadelivery.orders.entity.Status;

public record OrderResponseDto (
    Status status,
//    TODO: 주소 반환값
    String address
) {
    public static OrderResponseDto toDto(Order order) {
        return new OrderResponseDto(
                order.getStatus(),
                order.getAddress().toString()
        );
    }
}
