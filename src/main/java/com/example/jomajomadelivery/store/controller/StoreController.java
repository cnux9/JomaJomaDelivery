package com.example.jomajomadelivery.store.controller;

import com.example.jomajomadelivery.common.aop.account.CurrentUserId;
import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.dto.request.UpdateStoreRequestDto;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import com.example.jomajomadelivery.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {


    private final StoreService storeService;
    // view를 위해 "/new"사용. GET메소드 사용시 폼 으로 이동.
    @GetMapping("/new")
    public String addStoreForm() {
        return "/seller/store/CreateStoreForm";
    }

    @PostMapping("/new")
    public String addStore(@Valid @ModelAttribute StoreRequestDto dto, @CurrentUserId Long userId) {
        storeService.addStore(dto,userId);
        return "redirect:/stores/seller";
    }
//    @PostMapping("/new")
//    public ResponseEntity<Void> addStore(@Valid @RequestBody StoreRequestDto dto) {
//        storeService.addStore(dto);
//        return ResponseEntity.ok().build();
//    }


    //    @GetMapping
//    public ResponseEntity<Page<StoreResponseDto>> findAllStore(Pageable pageable) {
//        Page<StoreResponseDto> storeList = storeService.findAllStore(pageable);
//        return new ResponseEntity<>(storeList, HttpStatus.OK);
//    }
    @GetMapping
    public String findAllStore(Pageable pageable, Model model) {
        Page<StoreResponseDto> storeList = storeService.findAllStore(pageable);
        model.addAttribute("storeList", storeList);
        return "storesview";
    }

    @GetMapping("/seller")
    public String findAllStoreBySeller(Pageable pageable, Model model,@CurrentUserId Long userId) {
        Page<StoreResponseDto> storeList = storeService.findAllStoreBySeller(pageable,userId);
        model.addAttribute("storeList", storeList);
        return "/seller/store/storesview";
    }

    @GetMapping("/seller/{storeId}")
    public String sellerStoreMain(@PathVariable Long storeId,Model model) {
        model.addAttribute("storeId",storeId);
        return "/seller/store/SellerStoreMain";
    }
    @GetMapping("/seller/self/{storeId}")
    public String sellerSelfService(@PathVariable Long storeId,Model model) {
        model.addAttribute("storeId",storeId);
        return "/seller/store/StoreSelfHome";
    }

    @GetMapping("/{store_id}")
    public ResponseEntity<StoreResponseDto> findById(@PathVariable Long store_id) {
        StoreResponseDto responseDto = storeService.findById(store_id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/update/{store_id}")
    public String updateStoreForm(@PathVariable Long store_id, Model model) {
        model.addAttribute("store_id", store_id);
        return "/seller/store/UpdateStoreForm";
    }

    //    @PatchMapping("/{store_id}")
//    public ResponseEntity<StoreResponseDto> updateStore(@PathVariable Long store_id,
//                                                        UpdateStoreRequestDto dto) {
//        StoreResponseDto responseDto = storeService.updateStore(store_id, dto);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }
    @PatchMapping("/{store_id}")
    public String updateStore(@PathVariable Long store_id,
                              UpdateStoreRequestDto dto) {
        System.out.println(dto.description());
        StoreResponseDto responseDto = storeService.updateStore(store_id, dto);
        return "redirect:/stores/seller";
    }

    @DeleteMapping("/{store_id}")
    public ResponseEntity<Void> shutDownStore(@PathVariable Long store_id) {
        storeService.shutDownStore(store_id);
        return ResponseEntity.ok().build();
    }
}
