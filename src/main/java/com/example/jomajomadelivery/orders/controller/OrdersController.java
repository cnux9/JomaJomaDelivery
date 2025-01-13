package com.example.jomajomadelivery.orders.controller;

import com.example.jomajomadelivery.common.aop.account.CurrentUserId;
import com.example.jomajomadelivery.orders.dto.request.OrdersRequestDto;
import com.example.jomajomadelivery.orders.dto.response.OrderResponseDto;
import com.example.jomajomadelivery.orders.service.OrdersService;
import com.example.jomajomadelivery.user.entity.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
@Secured(RoleConstants.ROLE_USER)
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@CurrentUserId Long userId, @RequestBody OrdersRequestDto dto) {
        return new ResponseEntity<>(ordersService.createOrder(userId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> find(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.find(orderId));
    }

    @GetMapping
    public String findAllByUser(
            @CurrentUserId Long userId, Pageable pageable, Model model) {
        Page<OrderResponseDto> orders = ordersService.findAllByUser(userId, pageable);
        model.addAttribute("orders", orders);
        return "UserOrderList";
    }

    @GetMapping("/store/{storeId}")
    public String findAllByStore(@PathVariable Long storeId, Pageable pageable, Model model,
                                 @RequestParam(defaultValue = "false") boolean completed) {
        Page<OrderResponseDto> orders = ordersService.findAllByStore(storeId, pageable, completed);
        model.addAttribute("orders", orders);
        return "/seller/store/StoreOrder";
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long orderId) {
        ordersService.updateOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> delete(@PathVariable Long orderId) {
        ordersService.delete(orderId);
        return ResponseEntity.ok().build();
    }

}
