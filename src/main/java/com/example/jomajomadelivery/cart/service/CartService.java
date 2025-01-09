package com.example.jomajomadelivery.cart.service;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.entity.CartStatus;
import com.example.jomajomadelivery.cart.repository.CartRepository;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public Cart addCart() {
        User user = userRepository.findById(1L).get();
        Cart cart = cartRepository.findCartByUserAndStatus(user, CartStatus.ORDERING).orElseGet(() ->
                cartRepository.save(Cart.newCart(user))
        );
        return cart;
    }
}
