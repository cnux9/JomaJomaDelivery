package com.example.jomajomadelivery.orders.repository;

import com.example.jomajomadelivery.orders.entity.Order;
import com.example.jomajomadelivery.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
