package com.example.jomajomadelivery.menu.entity;

import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menu_id;

    @ManyToOne
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

    public Menu(Store store, MenuRequestDto dto) {
        this.store = store;
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.img_path = dto.getImg_path();
    }

}
