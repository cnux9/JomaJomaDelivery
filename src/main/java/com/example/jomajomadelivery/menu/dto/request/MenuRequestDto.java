package com.example.jomajomadelivery.menu.dto.request;

import lombok.Getter;

@Getter
public class MenuRequestDto { //Todo: record
    private String name;
    private String description;
    private int price;
    private String img_path;
}
