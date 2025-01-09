package com.example.jomajomadelivery.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuResponseDto {
    private Long id;
    private String name;
    private String description;
    private int price;
    private String img_path;
}
