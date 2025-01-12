package com.example.jomajomadelivery.store.controller;

import com.example.jomajomadelivery.common.aop.account.CurrentUserId;
import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.dto.request.UpdateStoreRequestDto;
import com.example.jomajomadelivery.store.dto.response.StoreAndMenusResponseDto;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import com.example.jomajomadelivery.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public String findAllStore(Pageable pageable,
                               Model model,
                               @RequestParam(required = false) String query,
                               @RequestParam(required = false) String category
                               ) {
        Page<StoreResponseDto> storeList = storeService.findAllStoreByFilter(pageable,query,category);
        model.addAttribute("storeList", storeList);
        return "storesview";
    }

    // 사장님 본인의 가게 조회
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
        model.addAttribute("storeName",storeService.findById(storeId).name());
        model.addAttribute("sellerName",storeService.findSellerNameById(storeId));
        return "/seller/store/StoreSelfHome";
    }

    @GetMapping("/{store_id}")
    public String findById(@PathVariable Long storeId,Model model) {
        StoreAndMenusResponseDto responseDto = storeService.findById(storeId);
        model.addAttribute("store",responseDto);
        return "/StoreDetail";
    }

    @GetMapping("/update/{storeId}")
    public String updateStoreForm(@PathVariable Long storeId, Model model) {
        StoreAndMenusResponseDto responseDto = storeService.findById(storeId);
        model.addAttribute("storeId", storeId);
        model.addAttribute("storeDto",responseDto);
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
                             @ModelAttribute UpdateStoreRequestDto dto) {
        storeService.updateStore(store_id, dto);
        return "redirect:/stores/seller";
    }

    @DeleteMapping("/{store_id}")
    public ResponseEntity<Void> shutDownStore(@PathVariable Long store_id) {
        storeService.shutDownStore(store_id);
        return ResponseEntity.ok().build();
    }
}
