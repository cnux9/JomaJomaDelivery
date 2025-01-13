package com.example.jomajomadelivery.orders.entity;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(nullable = false)
    private Long addressId;

    private int totalOrderPrice;

    public static Order newOrders(User user, Store store, Cart cart,Long addressId,int totalOrderPrice) {
        return Order.builder()
                .user(user)
                .store(store)
                .cart(cart)
                .status(Status.ORDERED)
                .addressId(addressId)
                .totalOrderPrice(totalOrderPrice)
                .build();
    }

    public Order updateStatus() {
        System.out.println(status);
        if(this.status.equals(Status.ORDERED)) {
            this.status=Status.IN_PROGRESS;
            return this;
        }
        if(this.status==Status.IN_PROGRESS) {
            this.status=Status.DELIVERED;
            return this;
        }
        return this;
    }
}
