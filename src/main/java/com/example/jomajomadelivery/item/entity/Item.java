package com.example.jomajomadelivery.item.entity;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    public static Item selectItme(Cart cart, Menu menu, int quantity) {
        return Item.builder()
                .cart(cart)
                .menu(menu)
                .name(menu.getName())
                .quantity(quantity)
                .price(menu.getPrice())
                .totalPrice(menu.getPrice() * quantity)
                .build();
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = quantity * this.menu.getPrice();
    }

    public void plusQuantity(int quantity) {
        this.quantity += quantity;
        this.totalPrice = this.quantity * this.menu.getPrice();
    }
}
