package com.example.jomajomadelivery.item.controller;

import com.example.jomajomadelivery.cart.dto.request.AddCartRequestDto;
import com.example.jomajomadelivery.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts/{cartId}/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Void> addItem(
            @PathVariable Long cartId,
            @RequestAttribute("dto") AddCartRequestDto dto
    ) {
        itemService.addItem(cartId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{itemId}")
    public String updateQuantity(@PathVariable Long itemId, @RequestParam int quantity) {
        itemService.updateQuantity(itemId,quantity);
        return "redirect:/items";
    }

    @DeleteMapping("/{itemId}")
    public String deleteItem(@PathVariable Long itemId){
        itemService.deleteItem(itemId);
        return "redirect:/items";
    }
}
