package com.example.jomajomadelivery.favorite.repository;

import com.example.jomajomadelivery.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

//    @Query("""
//        SELECT new com.example.jomajomadelivery.store.dto.response.StoreResponseDto(
//            s.storeId,
//            s.category,
//            s.name,
//            s.description,
//            s.address,
//            s.phoneNumber,
//            s.imgPath,
//            s.openTime,
//            s.closeTime,
//            s.rating,
//            s.minOrderPrice,
//            s.deliveryPrice,
//            s.favoriteCount
//        )
//        FROM Favorite f INNER JOIN Store AS s ON f.store.storeId = s.storeId
//        WHERE f.user.userId = :userId
//    """)
//    List<StoreResponseDto> findFavoriteStoreIdsByUserId(@Param("userId") Long userId);


    @Query("""
        SELECT f.store.storeId
        FROM Favorite f
        WHERE f.user.userId = :userId
    """)
    List<Long> findFavoriteStoreIdsByUserId(Long userId);


    void deleteByUserUserIdAndStoreStoreId(Long userId, Long storeId);

    Long countFavoritesByStoreStoreId(Long storeId);
}
