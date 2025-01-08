package com.example.jomajomadelivery.menu.controller;

import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.menu.dto.response.MenuResponseDto;
import com.example.jomajomadelivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stores/{storeId}/menus")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public void createMenu(@PathVariable Long storeId, @RequestBody MenuRequestDto menuRequestDto) {
        menuService.createMenu(storeId, menuRequestDto);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDto>> getMenus(@PathVariable Long storeId) {
        return ResponseEntity.ok(menuService.getMenus(storeId));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long storeId, @PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenu(storeId, menuId));
    }
}
