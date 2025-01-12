package com.example.jomajomadelivery.favorite.service;

import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.favorite.entity.Favorite;
import com.example.jomajomadelivery.favorite.repository.FavoriteRepository;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.exception.StoreErrorCode;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public void add(Long storeId) {
        // TODO:
        Long userId = 1L;
        User userRef = userRepository.getReferenceById(userId);
        Store foundStore = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(StoreErrorCode.STORE_NOT_FOUND));

        Favorite newFavorite = Favorite.of(userRef, foundStore);
        favoriteRepository.save(newFavorite);

        updateStoreFavoriteCount(foundStore);
    }

    public List<StoreResponseDto> find() {
        // TODO:
        Long userId = 1L;
        return favoriteRepository.findFavoriteStoreIdsByUserId(userId);
    }

    public void delete(Long storeId) {
        // TODO:
        Long userId = 1L;
        favoriteRepository.deleteByUserUserIdAndStoreStoreId(userId, storeId);
        Store foundStore = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(StoreErrorCode.STORE_NOT_FOUND));

        updateStoreFavoriteCount(foundStore);
    }

    private void updateStoreFavoriteCount(Store store) {
        // TODO: 쿼리 메소드 작동 확인
        Long favoriteCount = favoriteRepository.countFavoritesByStoreStoreId(store.getStoreId());
        store.updateFavoriteCount(favoriteCount);
    }
}
