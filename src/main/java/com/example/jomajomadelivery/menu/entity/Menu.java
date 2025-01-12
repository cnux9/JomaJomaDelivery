package com.example.jomajomadelivery.menu.entity;

import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menus")
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "img_path")
    private String imgPath;

    public static Menu newMenu(Store store, MenuRequestDto dto,String imgPath) {
        return Menu.builder()
                .store(store)
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .imgPath(imgPath)
                .build();
    }

    public void updateMenu(MenuRequestDto dto,String imgPath){
        this.name=dto.name();
        this.description=dto.description();
        this.price=dto.price();
        this.imgPath =imgPath;
    }

}
