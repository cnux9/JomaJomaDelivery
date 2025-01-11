package com.example.jomajomadelivery.orders.service;

import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.repository.CartRepository;
import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.orders.dto.response.OrderResponseDto;
import com.example.jomajomadelivery.orders.entity.Order;
import com.example.jomajomadelivery.orders.exception.OrderErrorCode;
import com.example.jomajomadelivery.orders.repository.OrdersRepository;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.entity.Role;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final CartRepository cartRepository;

    //Todo:: User, Store, Cart, Address 주입 필요
    public OrderResponseDto create() {
        User user = userRepository.findById(1L).get();
        Store store = storeRepository.findById(1L).get();
        Cart cart = cartRepository.findById(1L).get();
        Address address = user.getAddresses().get(0);

        throwIfTotalPriceIsLowerThanMinOrderPrice(cart, store);
        throwIfStoreIsNotOpen(store);

        Order order = Order.newOrders(user, store, cart, address);
        Order savedOrder = ordersRepository.save(order);

        return OrderResponseDto.toDto(savedOrder);
    }

    public OrderResponseDto find(Long orderId) {
        Order order = getById(orderId);
        return OrderResponseDto.toDto(order);
    }

    @Transactional
    public void update(Long orderId) {
        // FIXME:
        User loginUser = userRepository.getReferenceById(1L);
        if (loginUser.getRole().equals(Role.ROLE_USER)) {
            throw new CustomException(OrderErrorCode.USER_CHANGES_ORDER_STATUS);
        }

        Order order = getById(orderId);
        //Todo:: try catch 문 리팩토링 가능하면 필요
        try {
            order.updateStatus();
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long orderId) {
        Order order = getById(orderId);
        ordersRepository.delete(order);
    }

    /**
     * Orders 객체 가져오며 예외처리
     */
    private Order getById(Long orderId) {
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("요청하신 주문내역이 없습니다."));
    }

    private static void throwIfTotalPriceIsLowerThanMinOrderPrice(Cart cart, Store store) {
        int totalPrice = cart.getItems().stream().mapToInt(i -> i.getTotalPrice()).sum();
        if (totalPrice< store.getMinOrderPrice()) {
            throw new CustomException(OrderErrorCode.LOWER_THAN_MIN_ORDER_PRICE);
        }
    }

    private static void throwIfStoreIsNotOpen(Store store) {
        LocalTime now = LocalTime.now();
        if (now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
            throw new CustomException(OrderErrorCode.STORE_NOT_OPEN);
        }
    }
}
