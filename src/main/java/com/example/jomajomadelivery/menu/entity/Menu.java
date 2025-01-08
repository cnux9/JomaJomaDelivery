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
    private Long menu_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "img_path")
    private String img_path;

    public static Menu newMenu(Store store, MenuRequestDto dto) {
        return Menu.builder()
                .store(store)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .img_path(dto.getImg_path())
                .build();
    }

    public void updateMenu(MenuRequestDto dto){
        this.name=dto.getName();
        this.description=dto.getDescription();
        this.price=dto.getPrice();
        this.img_path=dto.getImg_path();
    }

}
