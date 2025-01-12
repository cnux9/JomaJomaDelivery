package com.example.jomajomadelivery.menu.controller;

import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.menu.dto.response.MenuResponseDto;
import com.example.jomajomadelivery.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/stores/{storeId}/menus")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/new")
    public String createMenuForm(@PathVariable Long storeId, Model model) {
        model.addAttribute("storeId", storeId);
        return "/seller/store/CreateMenuForm";
    }

    @PostMapping
    public String createMenu(@PathVariable Long storeId, @ModelAttribute MenuRequestDto menuRequestDto) {
        menuService.createMenu(storeId, menuRequestDto);
        return "redirect:/stores/" + storeId + "/menus";
    }

    @GetMapping
    public String getMenus(@PathVariable Long storeId, Model model) {
        model.addAttribute("menus", menuService.getMenus(storeId));
        model.addAttribute("storeId",storeId);
        return "/seller/store/MenuList";
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenu(menuId));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<String> updateMenu(@PathVariable Long menuId, @ModelAttribute MenuRequestDto menuRequestDto) {
        menuService.updateMenu(menuId, menuRequestDto);
        return ResponseEntity.ok("메뉴 수정 성공");
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok("메뉴 삭제 성공");
    }
}
