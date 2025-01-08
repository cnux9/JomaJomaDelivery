package com.example.jomajomadelivery.menu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Menu {
    @Id
    private Long menu_id;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    private String name;
    private String description;
    private int price;
    private String img_path;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Menu(Store store, String name, String description, int price, String img_path) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.img_path = img_path;
    }
}
