package com.example.jomajomadelivery.orders.dto.response;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.item.entity.Item;
import com.example.jomajomadelivery.orders.entity.Order;
import com.example.jomajomadelivery.orders.entity.Status;
import com.example.jomajomadelivery.store.entity.Store;

import java.time.LocalDateTime;

public record OrderResponseDto(
        Long orderId,
        Status status,
        String address,
        Store store,
        Cart cart,
        String menu,
        int totalOrderPrice,
        int totalItemQuantity,
        LocalDateTime createdAt

) {
    public static OrderResponseDto toDto(Order order, String address) {
        String menu = order.getCart().getItems().get(0).getMenu().getName();
        int totalItemQuantitynu = order.getCart().getItems().stream()
                .mapToInt(Item::getQuantity)
                .sum();
        return new OrderResponseDto(
                order.getOrderId(),
                order.getStatus(),
                address,
                order.getStore(),
                order.getCart(),
                menu,
                order.getTotalOrderPrice(),
                totalItemQuantitynu,
                order.getCreatedAt()
        );
    }
}
