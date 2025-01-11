package com.example.jomajomadelivery.orders.service;

import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.repository.CartRepository;
import com.example.jomajomadelivery.orders.dto.response.OrdersResponseDto;
import com.example.jomajomadelivery.orders.entity.Orders;
import com.example.jomajomadelivery.orders.repository.OrdersRepository;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.NoSuchElementException;

@Slf4j
@Aspect
@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final CartRepository cartRepository;

    @Pointcut("execution(* com.example.jomajomadelivery.orders.service.OrdersService.createOrder(..)) ||" +
    "execution(* com.example.jomajomadelivery.orders.service.OrdersService.updateOrder(..))")
    public void ordersServicePointcut() {}

    @AfterReturning(pointcut = "ordersServicePointcut()",returning = "result")
    public void logOrderActivity(Object result) {
        if (result instanceof Orders order) {
            log.info("Order Log - 상태: {}, 요청 시각: {}, 가게 ID: {}, 주문 ID: {}",
                    order.getStatus(),
                    LocalTime.now(),
                    order.getStore().getStoreId(),
                    order.getOrderId());
        }
    }

    //Todo:: User, Store, Cart, Address 주입 필요
    public OrdersResponseDto createOrder() {
        User user = userRepository.findById(1L).get();
        Store store = storeRepository.findById(1L).get();
        Cart cart = cartRepository.findById(1L).get();
        Address address = user.getAddresses().get(0);

        Orders orders = Orders.newOrders(user, store, cart, address);
        ordersRepository.save(orders);
        return new OrdersResponseDto(orders.getStatus(), orders.getAddress());
    }

    public OrdersResponseDto getOrder(Long orderId) {
        Orders orders = getOrders(orderId);
        return new OrdersResponseDto(orders.getStatus(), orders.getAddress());
    }

    //Todo:: try catch 문 리팩토링 가능하면 필요
    public OrdersResponseDto updateOrder(Long orderId) {
        Orders orders = getOrders(orderId);
        try {
            orders.updateStatus();
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
        return new OrdersResponseDto(orders.getStatus(), orders.getAddress());
    }

    public void deleteOrder(Long orderId) {
        Orders orders = getOrders(orderId);
        ordersRepository.delete(orders);
    }

    /**
     * Orders 객체 가져오며 예외처리
     */
    private Orders getOrders(Long orderId) {
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("요청하신 주문내역이 없습니다."));
    }
}
