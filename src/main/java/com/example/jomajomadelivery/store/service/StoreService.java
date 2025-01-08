package com.example.jomajomadelivery.store.service;

import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.entity.Role;
import com.example.jomajomadelivery.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    public void addStore(StoreRequestDto dto) {
        User user = new User(1L,"aa","aa","dd", Role.SELLER,"dd","dd",false);
        Store store = Store.addStore(user,dto);
        //Todo dto에서 세부 주소를 따로 받고 store 엔티티 수정해야함.
        storeRepository.save(store);
    }

    //Todo: 요청에따라 필터링
    public Page<StoreResponseDto> findAllStore(Pageable pageable) {
        Page<Store> storeList = storeRepository.findAll(pageable);
        // Todo: 빈 배열일 경우 에러 던지깅

        return storeList.map(StoreResponseDto::toDTO);
    }
}
