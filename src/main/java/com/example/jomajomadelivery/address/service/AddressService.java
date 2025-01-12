package com.example.jomajomadelivery.address.service;

import com.example.jomajomadelivery.address.dto.request.AddressRequestDto;
import com.example.jomajomadelivery.address.dto.response.AddressResponseDto;
import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.address.repository.AddressRepository;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        Address address = Address.createAddress(dto);
        Address savedAddress = addressRepository.save(address);
        return AddressResponseDto.toDto(savedAddress);
    }

    @Transactional(readOnly = true)
    public AddressResponseDto findById(Long id) {
        Address foundAddress = getById(id);
        return AddressResponseDto.toDto(foundAddress);
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
        foundAddress.softDelete();
    }

    private Address getById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such address id: " + id));
    }
}
