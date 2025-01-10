package com.example.jomajomadelivery.orders.entity;

import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.apache.coyote.BadRequestException;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Orders extends BaseEntity {

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

    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    public static Orders newOrders(User user, Store store, Cart cart, Address address) {
        return Orders.builder()
                .user(user)
                .store(store)
                .cart(cart)
                .status(Status.ORDERED)
                .address(address)
                .build();
    }

    public void updateStatus() throws BadRequestException {
        if(this.status==Status.ORDERED) {
            this.status=Status.InProgress;
        }
        if(this.status==Status.InProgress) {
            this.status=Status.DELIVERED;
        }
        if(this.status==Status.DELIVERED) {
            throw new BadRequestException("이미 배달이 완료된 주문입니다.");
        }
    }
}
