package com.example.jomajomadelivery.store.controller;

import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import com.example.jomajomadelivery.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {


    private final StoreService storeService;
    @PostMapping("/new")
    public ResponseEntity<Void> addStore(@Valid @RequestBody StoreRequestDto dto){
        storeService.addStore(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<StoreResponseDto>> findAllStore(Pageable pageable){
        Page<StoreResponseDto> storeList = storeService.findAllStore(pageable);
        return new ResponseEntity<>(storeList, HttpStatus.OK);
    }
}
