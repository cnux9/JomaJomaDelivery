package com.example.jomajomadelivery.item.controller;

import com.example.jomajomadelivery.cart.dto.requestDto.AddCartRequestDto;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Void> addItem(
            @RequestAttribute("dto") AddCartRequestDto dto,
            @RequestAttribute("cart") Cart cart) {
        System.out.println("item " + dto.menu_id());
        System.out.println("item " + dto.quantity());
        itemService.addItem(dto, cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/cartId")
//    public ResponseEntity<List<ItemResponseDto>> findAllItem(@PathVariable Long cartId) {
//        List<ItemResponseDto> itemList = itemService.findAllItem(cartId);
//        return new ResponseEntity<>(itemList, HttpStatus.OK);
//    }
}
