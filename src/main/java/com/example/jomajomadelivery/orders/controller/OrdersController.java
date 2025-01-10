package com.example.jomajomadelivery.orders.controller;

import com.example.jomajomadelivery.orders.dto.response.OrdersResponseDto;
import com.example.jomajomadelivery.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public void createOrder() {
        ordersService.createOrder();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponseDto> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.getOrder(orderId));
    }

    @PatchMapping("/{orderId}")
    public void updateOrder(@PathVariable Long orderId) {
        ordersService.updateOrder(orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        ordersService.deleteOrder(orderId);
    }

}
