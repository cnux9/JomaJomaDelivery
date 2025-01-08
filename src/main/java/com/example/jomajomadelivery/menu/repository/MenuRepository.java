package com.example.jomajomadelivery.menu.repository;

import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);
}
