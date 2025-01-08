package com.example.jomajomadelivery.store.repository;

import com.example.jomajomadelivery.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Page<Store> findAll(Pageable pageable);
}
