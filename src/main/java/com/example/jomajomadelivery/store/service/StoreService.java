package com.example.jomajomadelivery.store.service;

import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    public void addStore(StoreRequestDto dto) {
        Store store = new Store();
        //todo: userid대신 유저 객체(로그인한 관리자)넣어야함.
        store.addStore(1L,dto);
        storeRepository.save(store);
    }
}
