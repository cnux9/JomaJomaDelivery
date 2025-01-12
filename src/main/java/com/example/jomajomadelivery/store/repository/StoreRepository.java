package com.example.jomajomadelivery.store.repository;

import com.example.jomajomadelivery.store.entity.Category;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Page<Store> findAll(Pageable pageable);
    Page<Store> findAllByUser(User user, Pageable pageable);


    @Query("""
        SELECT COUNT(*)
        FROM Store s
        WHERE s.user.userId = :userId
    """)
    int countByUserId(@Param("userId") Long userId);

    @Query("""
    SELECT DISTINCT s 
    FROM Store s 
    LEFT JOIN s.menus m 
    WHERE (:query IS NULL OR s.name LIKE %:query% OR m.name LIKE %:query%)
      AND (:category IS NULL OR s.category = :category)
""")
    Page<Store> searchStoresByKeywordAndCategory(
            @Param("query") String query,
            @Param("category") Category category,
            Pageable pageable
    );


}
