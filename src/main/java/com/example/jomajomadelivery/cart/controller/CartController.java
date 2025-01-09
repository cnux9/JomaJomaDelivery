package com.example.jomajomadelivery.cart.controller;

import com.example.jomajomadelivery.cart.dto.requestDto.AddCartRequestDto;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts") //todo url은 팀원들과 상의 후 정리해야될듯합니다.
public class CartController {
    private final CartService cartService;

    @PostMapping
    public String addCart(@RequestBody AddCartRequestDto dto, Model model) {
        Cart cart = cartService.addCart();
        model.addAttribute("dto", dto);
        model.addAttribute("cart", cart);
        System.out.println("cart "+dto.menu_id());
        System.out.println("cart "+dto.quantity());
        return "forward:/items";
    }

}
