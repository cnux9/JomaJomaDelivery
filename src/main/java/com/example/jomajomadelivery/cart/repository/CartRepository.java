package com.example.jomajomadelivery.cart.repository;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.cart.entity.CartStatus;
import com.example.jomajomadelivery.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findCartByUserAndStatus(User user, CartStatus status);
    Optional<Cart> findCartByCartIdAndStatus(Long cartId, CartStatus status);
    Optional<Cart> findByUser(User user);
}
