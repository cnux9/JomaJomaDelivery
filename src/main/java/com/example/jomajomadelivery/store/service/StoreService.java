package com.example.jomajomadelivery.store.service;

import com.example.jomajomadelivery.address.dto.request.AddressRequestDto;
import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.address.exception.AddressErrorCode;
import com.example.jomajomadelivery.address.repository.AddressRepository;
import com.example.jomajomadelivery.common.ImageHandler;
import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.dto.request.UpdateStoreRequestDto;
import com.example.jomajomadelivery.store.dto.response.StoreAndMenusResponseDto;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import com.example.jomajomadelivery.store.entity.Category;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.exception.StoreErrorCode;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.entity.Role;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final AddressRepository addressRepository;
    private final ImageHandler imageHandler;

    public void addStore(StoreRequestDto dto, Long userId) {
        User user = userRepository.findById(userId).get();
        throwIfUserIsNotSeller(user);
        throwIfStoreIsMoreThanThree(user);
        AddressRequestDto addressRequestDto = new AddressRequestDto(
                dto.name(), dto.zipcode(), dto.state(),
                dto.city(), dto.street(), dto.detailAddress());
        Address address = Address.createAddress(addressRequestDto);

        String imgPath = imageHandler.save(dto.img(), "store");
        Store store = Store.addStore(user, dto, imgPath, address.getAddressId());
        store = storeRepository.save(store);
        addressRepository.save(address);
    }


    @Transactional(readOnly = true)
    public Page<StoreResponseDto> findAllStoreByFilter(Pageable pageable,
                                                       String query,
                                                       String category) {
        Category enumCategory = null;
        if (category != null && !category.isEmpty()) {
            enumCategory = Category.valueOf(category);
        }
        Page<Store> storeList = storeRepository.searchStoresByKeywordAndCategory(
                query, enumCategory, pageable);

        return storeList.map(store -> {
            // Address 조회 및 toString 사용
            String fullAddress = addressRepository.findById(store.getAddressId())
                    .map(Address::toString) // toString 메서드 호출로 풀 주소 가져오기
                    .orElse("Unknown Address"); // Address가 없으면 기본값

            return StoreResponseDto.toDTO(store, fullAddress);
        });
    }

    @Transactional
    public StoreResponseDto updateStore(Long storeId, UpdateStoreRequestDto dto) {
        Store store = getStore(storeId);
        String imgPath = store.getImgPath();
        if (dto.img() != null) {
            imgPath = imageHandler.save(dto.img(), "store");
        }
        store.updateStore(dto, imgPath);
        String address = getFullAddress(store.getAddressId());
        return StoreResponseDto.toDTO(store,address);

    }

    @Transactional(readOnly = true)
    public StoreAndMenusResponseDto findById(Long storeId) {
        Store store = storeRepository.findByIdWithMenus(storeId)
                .orElseThrow(() -> new CustomException(StoreErrorCode.STORE_NOT_FOUND));
        String address = getFullAddress(store.getAddressId());
        return StoreAndMenusResponseDto.toDTO(store,address);
    }

    @Transactional(readOnly = true)
    public String findSellerNameById(Long storeId) {
        Store store = getStore(storeId);
        return store.getUser().getName();
    }

    @Transactional
    public void shutDownStore(Long storeId) {
        Store store = getStore(storeId);
        store.shutDownStore();
    }

    @Transactional(readOnly = true)
    public Page<StoreResponseDto> findAllStoreBySeller(Pageable pageable, Long userId) {
        User user = userRepository.findById(userId).get();
        Page<Store> storeList = storeRepository.findAllByUser(user, pageable);
        return storeList.map(store -> {
            // Address 조회 및 toString 사용
            String fullAddress = addressRepository.findById(store.getAddressId())
                    .map(Address::toString) // toString 메서드 호출로 풀 주소 가져오기
                    .orElse("Unknown Address"); // Address가 없으면 기본값

            return StoreResponseDto.toDTO(store, fullAddress);
        });
    }

    private Store getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없슈"));
        if (store.getIsDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다.");
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
    private String getFullAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(()->new CustomException(AddressErrorCode.ADDRESS_NOT_FOUND));
        return address.toString();
    }

}
