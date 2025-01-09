package com.example.jomajomadelivery.item.controller;

import com.example.jomajomadelivery.cart.dto.requestDto.AddCartRequestDto;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.item.dto.response.ItemResponseDto;
import com.example.jomajomadelivery.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping()
    public ResponseEntity<List<ItemResponseDto>> findAllItem() {
        List<ItemResponseDto> itemList = itemService.findAllItem();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }
}
