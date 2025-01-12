package com.example.jomajomadelivery.orders.controller;

import com.example.jomajomadelivery.common.aop.account.CurrentUserId;
import com.example.jomajomadelivery.orders.dto.request.OrdersRequestDto;
import com.example.jomajomadelivery.orders.dto.response.OrderResponseDto;
import com.example.jomajomadelivery.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@CurrentUserId Long userId, @RequestBody OrdersRequestDto dto) {
        return new ResponseEntity<>(ordersService.createOrder(userId,dto), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> find(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.find(orderId));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.updateOrder(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> delete(@PathVariable Long orderId) {
        ordersService.delete(orderId);
        return ResponseEntity.ok().build();
    }

}
