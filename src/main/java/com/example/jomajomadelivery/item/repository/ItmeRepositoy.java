package com.example.jomajomadelivery.item.repository;

import com.example.jomajomadelivery.cart.entity.Cart;
import com.example.jomajomadelivery.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItmeRepositoy extends JpaRepository<Item,Long> {
    List<Item> findAllByCart(Cart cart);
}
