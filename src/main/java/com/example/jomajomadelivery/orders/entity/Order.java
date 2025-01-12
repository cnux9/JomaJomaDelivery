package com.example.jomajomadelivery.orders.entity;

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

    @Column(name = "status")
    private Status status;

    @Column(nullable = false)
    private Long addressId;

    public static Order newOrders(User user, Store store, Cart cart,Long addressId) {
        return Order.builder()
                .user(user)
                .store(store)
                .cart(cart)
                .status(Status.ORDERED)
                .addressId(addressId)
                .build();
    }

    public void updateStatus() throws BadRequestException {
        if(this.status==Status.ORDERED) {
            this.status=Status.IN_PROGRESS;
        }
        if(this.status==Status.IN_PROGRESS) {
            this.status=Status.DELIVERED;
        }
        if(this.status==Status.DELIVERED) {
            throw new BadRequestException("이미 배달이 완료된 주문입니다.");
//            TODO: CustomException으로 리팩토링
//            throw new CustomeException(OrderErrorCode.ALREADY_COMPLETE_ORDER);
        }
    }
}
