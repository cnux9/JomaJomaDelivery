package com.example.jomajomadelivery.address.controller;

import com.example.jomajomadelivery.address.dto.request.AddressRequestDto;
import com.example.jomajomadelivery.address.dto.response.AddressResponseDto;
import com.example.jomajomadelivery.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;


    @PostMapping
    public ResponseEntity<AddressResponseDto> create(@RequestBody AddressRequestDto dto) {
        return new ResponseEntity<>(addressService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);
    }

    // TODO: 다건 조회 -> 유저별 본인의 배송지 주소
    //                   사장별 본인의 가게들 주소
    @GetMapping
    public ResponseEntity<Page<AddressResponseDto>> findAllById(Pageable pageable) {
        return new ResponseEntity<>(addressService.findAllById(pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> update(@PathVariable Long id, @RequestBody AddressRequestDto dto) {
        return new ResponseEntity<>(addressService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
