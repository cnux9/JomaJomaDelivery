package com.example.jomajomadelivery.orders.repository;

import com.example.jomajomadelivery.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
