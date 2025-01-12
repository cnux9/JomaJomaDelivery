package com.example.jomajomadelivery.favorite.repository;

import com.example.jomajomadelivery.favorite.entity.Favorite;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("""
        SELECT new com.example.jomajomadelivery.store.dto.response.StoreResponseDto(
            s.storeId,
            s.category,
            s.name,
            s.description,
            s.address,
            s.phoneNumber,
            s.imgPath,
            s.openTime,
            s.closeTime,
            s.rating,
            s.minOrderPrice,
            s.deliveryPrice,
            s.favoriteCount
        )
        FROM (
            SELECT f.store.storeId
            FROM Favorite AS f
            WHERE f.user.userId = :userId
        ) AS i INNER JOIN Store AS s ON i.storeId = s.storeId
    """)
    List<StoreResponseDto> findFavoriteStoreIdsByUserId(@Param("userId") Long userId);

    void deleteByUserUserIdAndStoreStoreId(Long userId, Long storeId);

    Long countFavoritesByStoreStoreId(Long storeId);
}
