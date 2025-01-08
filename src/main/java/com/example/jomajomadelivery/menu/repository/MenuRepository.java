package com.example.jomajomadelivery.menu.repository;

import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);

    Optional<Menu> findByStoreAndMenu_id(Store store, Long menuId);
}
