package com.example.jomajomadelivery.item.controller;

import com.example.jomajomadelivery.cart.dto.request.AddCartRequestDto;
import com.example.jomajomadelivery.item.dto.request.UpdateQuantityRequestDto;
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
    public ResponseEntity<Void>  updateQuantity(@PathVariable Long itemId, @RequestBody UpdateQuantityRequestDto dto) {
        System.out.println("itemId = " + itemId);
        System.out.println("quantity = " + dto.quantity());
        itemService.updateQuantity(itemId,dto.quantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId){
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().build();
    }
}
