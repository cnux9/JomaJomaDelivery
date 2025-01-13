package com.example.jomajomadelivery.orders.repository;

import com.example.jomajomadelivery.orders.entity.Order;
import com.example.jomajomadelivery.orders.entity.Status;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    Page<Order> findAllByStoreAndStatus(Store store, Status status, Pageable pageable);

    Page<Order> findAllByStoreAndStatusIn(Store store, List<Status> statuses, Pageable pageable);

}
