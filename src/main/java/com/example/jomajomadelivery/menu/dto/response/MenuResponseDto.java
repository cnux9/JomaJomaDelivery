package com.example.jomajomadelivery.menu.dto.response;

import com.example.jomajomadelivery.menu.entity.Menu;

public record MenuResponseDto(
        Long id,
        String name,
        String description,
        int price,
        String imgPath
) {
    public static MenuResponseDto toDto(Menu menu) {
        return new MenuResponseDto(menu.getMenuId(), menu.getName(), menu.getDescription(), menu.getPrice(), menu.getImgPath());
    }
}