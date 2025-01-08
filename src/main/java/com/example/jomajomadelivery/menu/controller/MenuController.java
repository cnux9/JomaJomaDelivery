package com.example.jomajomadelivery.menu.controller;

import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.menu.dto.response.MenuResponseDto;
import com.example.jomajomadelivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores/{storeId}")
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/menu")
    public void createMenu(@PathVariable Long storeId, @RequestBody MenuRequestDto menuRequestDto) {
        menuService.createMenu(storeId, menuRequestDto);
    }
}
