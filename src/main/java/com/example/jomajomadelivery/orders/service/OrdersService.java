package com.example.jomajomadelivery.orders.service;

import com.example.jomajomadelivery.account.exception.LoginErrorCode;
import com.example.jomajomadelivery.address.repository.AddressRepository;
import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.repository.CartRepository;
import com.example.jomajomadelivery.cart.service.CartService;
import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.item.entity.Item;
import com.example.jomajomadelivery.orders.dto.request.OrdersRequestDto;
import com.example.jomajomadelivery.orders.dto.response.OrderResponseDto;
import com.example.jomajomadelivery.orders.entity.Order;
import com.example.jomajomadelivery.orders.entity.Status;
import com.example.jomajomadelivery.orders.exception.OrderErrorCode;
import com.example.jomajomadelivery.orders.repository.OrdersRepository;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
<<<<<<< Updated upstream
import org.apache.coyote.BadRequestException;
=======
import org.aspectj.lang.annotation.AfterReturning;
>>>>>>> Stashed changes
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
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
    private final AddressRepository addressRepository;
    private final CartService cartService;

    //Todo:: User, Store, Cart, Address 주입 필요
    public OrderResponseDto createOrder(Long userId, OrdersRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(LoginErrorCode.NEED_LOGIN));
        Store store = storeRepository.findById(1L).get();
        Cart cart = cartRepository.findById(1L).get();

        throwIfCartIsEmpty(cart);
        throwIfTotalPriceIsLowerThanMinOrderPrice(cart, store);
        throwIfStoreIsNotOpen(store);

        Order order = Order.newOrders(user, store, cart, user.getAddressId(), dto.totalOrderPrice());
        Order savedOrder = ordersRepository.save(order);
        cart = cart.UpdateCartStatus();
        cartRepository.save(cart);
        return OrderResponseDto.toDto(savedOrder, addressToString(savedOrder.getAddressId()));
    }

    private void throwIfCartIsEmpty(Cart cart) {
        if (cart.getItems().isEmpty()) {
            throw new CustomException(OrderErrorCode.EMPTY_CART);
        }
    }

    public OrderResponseDto find(Long orderId) {
        Order order = getById(orderId);
        return OrderResponseDto.toDto(order, addressToString(order.getAddressId()));
    }

    public Page<OrderResponseDto> findAllByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(LoginErrorCode.NEED_LOGIN));
        // 최신순으로 주문 목록 가져오기
        Page<Order> orders = ordersRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);
        return orders.map(order -> OrderResponseDto.toDto(order, addressToString(order.getAddressId())));

    }

    public Page<OrderResponseDto> findAllByStore(Long storeId, Pageable pageable, boolean completed) {
        Store store = storeRepository.findById(storeId).get();
        Page<Order> orders;
        if (completed) {
            orders = ordersRepository.findAllByStoreAndStatus(store, Status.DELIVERED, pageable);
        } else {
            List<Status> statuses = Arrays.asList(Status.ORDERED, Status.IN_PROGRESS);
            orders = ordersRepository.findAllByStoreAndStatusIn(store, statuses, pageable);
        }
        return orders.map(order -> OrderResponseDto.toDto(order, addressToString(order.getAddressId())));

    }

    @Transactional
    public void updateOrder(Long orderId) {

        Order order = getById(orderId);
        order = order.updateStatus();
        ordersRepository.save(order);
    }

    public void delete(Long orderId) {
        Order order = getById(orderId);
        ordersRepository.delete(order);
    }

    /**
     * Orders 객체 가져오며 예외처리
     */
    public Order getById(Long orderId) {
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("요청하신 주문내역이 없습니다."));
    }

    private static void throwIfTotalPriceIsLowerThanMinOrderPrice(Cart cart, Store store) {
        int totalPrice = cart.getItems().stream().mapToInt(Item::getTotalPrice).sum();
        if (totalPrice < store.getMinOrderPrice()) {
            throw new CustomException(OrderErrorCode.LOWER_THAN_MIN_ORDER_PRICE);
        }
    }

    private static void throwIfStoreIsNotOpen(Store store) {
        LocalTime now = LocalTime.now();
        if (now.isBefore(store.getOpenTime()) || now.isAfter(store.getCloseTime())) {
            throw new CustomException(OrderErrorCode.STORE_NOT_OPEN);
        }
    }

    private String addressToString(Long addressId) {
        return addressRepository.findById(addressId).get().toString();
    }

}
