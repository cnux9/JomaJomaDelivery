package com.example.jomajomadelivery.store.service;

import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    public void addStore(StoreRequestDto dto) {
//        User user = new User(1L,"aa","aa","dd", Role.SELLER,"dd","dd",false);
//        Store store =Store.addStore(user,dto);
//        //Todo dto에서 세부 주소를 따로 받고 store 엔티티 수정해야함.
//        storeRepository.save(store);
    }
}
