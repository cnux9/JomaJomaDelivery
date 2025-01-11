package com.example.jomajomadelivery.address.service;

import com.example.jomajomadelivery.address.dto.request.AddressRequestDto;
import com.example.jomajomadelivery.address.dto.response.AddressResponseDto;
import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.address.repository.AddressRepository;
import com.example.jomajomadelivery.store.repository.StoreRepository;
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
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;


    @Transactional
    public AddressResponseDto create(AddressRequestDto dto) {
        // 유저면 본인인지 확인
        // 가게면 사장님인지 확인
//        HasAddress entity = switch (dto.type()) {
//            case USER -> userRepository.findById(dto.entityId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user id: " + dto.entityId()));
//            case STORE -> storeRepository.findById(dto.entityId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such store id: " + dto.entityId()));
//        };
        Address address = Address.createAddress(dto);
        Address savedAddress = addressRepository.save(address);
        return AddressResponseDto.toDto(savedAddress);
    }

    // TODO: 트랜잭션 필요성?
    @Transactional(readOnly = true)
    public AddressResponseDto findById(Long id) {
        Address foundReview = getById(id);
        return AddressResponseDto.toDto(foundReview);
    }

    @Transactional(readOnly = true)
    public Page<AddressResponseDto> findAllById(Pageable pageable) {
        // 사장일 경우 가게들의 주소 반환

        // 유저일 경우 배송지들의 주소 반환

        Long userId = 1L;
        return addressRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public AddressResponseDto update(Long id, AddressRequestDto dto) {
        Address foundAddress = getById(id);
        Address newAddress = foundAddress.getUpdatedAddress(dto);
        Address savedAddress = addressRepository.save(newAddress);
        foundAddress.softDelete();
        return AddressResponseDto.toDto(savedAddress);
    }

    @Transactional
    public void delete(Long id) {
        // TODO: 유저가 주소의 주인인지 확인?
        Address foundAddress = getById(id);
        // TODO: 주소가 고아라면 -> 이 주소를 참조하는 오더가 하나도 없다면 -> Hard Delete?
        foundAddress.softDelete();
    }

    private Address getById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such address id: " + id));
    }
}
