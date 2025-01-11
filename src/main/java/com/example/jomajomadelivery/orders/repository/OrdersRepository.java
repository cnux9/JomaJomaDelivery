package com.example.jomajomadelivery.orders.repository;

import com.example.jomajomadelivery.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Order, Long> {
}
