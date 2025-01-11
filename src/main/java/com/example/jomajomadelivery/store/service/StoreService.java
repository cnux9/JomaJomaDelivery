package com.example.jomajomadelivery.store.service;

import com.example.jomajomadelivery.auth.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.dto.request.UpdateStoreRequestDto;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.exception.StoreErrorCode;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.entity.Role;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    public void addStore(StoreRequestDto dto) {
        // 테스트용 유저 생성
        SignUpUserDto signUpUserDto = new SignUpUserDto("aa","aa","dd","dd","dd","","","","","","", Role.ROLE_USER);
        User user = User.createUser(signUpUserDto);
        userRepository.save(user);

        throwIfUserIsNotSeller(user);
        throwIfStoreIsMoreThanThree(user);

        Store store =Store.addStore(user,dto);
//        //Todo dto에서 세부 주소를 따로 받고 store 엔티티 수정해야함.
        storeRepository.save(store);
    }

    //Todo: 요청에따라 필터링

    @Transactional(readOnly = true)
    public Page<StoreResponseDto> findAllStore(Pageable pageable) {
        Page<Store> storeList = storeRepository.findAll(pageable);
        // Todo: 빈 배열일 경우 에러 던지깅

        return storeList.map(StoreResponseDto::toDTO);
    }
    @Transactional
    public StoreResponseDto updateStore(Long storeId, UpdateStoreRequestDto dto) {
        Store store = getStore(storeId);
        store.updateStore(dto);
        return StoreResponseDto.toDTO(store);

    }

    @Transactional(readOnly = true)
    public StoreResponseDto findById(Long storeId) {
        Store store = getStore(storeId);
        return StoreResponseDto.toDTO(store);
    }

    @Transactional
    public void shutDownStore(Long storeId) {
        Store store = getStore(storeId);
        store.shutDownStore();
    }

    @Transactional
    public Page<StoreResponseDto> findAllStoreBySeller(Pageable pageable) {
        User user = userRepository.findById(1L).get();
        PageImpl<StoreResponseDto> storeList = new PageImpl<>(storeRepository.findAllByUser(user,pageable).stream().filter(s -> !s.getIsDeleted()).map(StoreResponseDto::toDTO).toList());
        // Todo: 빈 배열일 경우 에러 던지깅

        return storeList;
    }
    private Store getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"없슈"));
        if (store.getIsDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"없슈");
        }
        return store;
    }

    private void throwIfStoreIsMoreThanThree(User user) {
        if (storeRepository.countByUserId(user.getUserId()) == 3) {
            throw new CustomException(StoreErrorCode.EXCEED_MAX_STORE);
        }
    }

    private void throwIfUserIsNotSeller(User user) {
        if (user.getRole().equals(Role.ROLE_USER)) {
            throw new CustomException(StoreErrorCode.NOT_SELLER);
        }
    }
}
