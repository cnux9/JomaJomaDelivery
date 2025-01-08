package com.example.jomajomadelivery.menu.service;

import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.menu.dto.response.MenuResponseDto;
import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.menu.repository.MenuRepository;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public void createMenu(Long storeId, MenuRequestDto menuRequestDto) {
        Store store = getStore(storeId);
        Menu menu = new Menu(store, menuRequestDto);
        menuRepository.save(menu);
    }

    public List<MenuResponseDto> getMenus(Long storeId) {
        Store store = getStore(storeId);
        List<Menu> menus = menuRepository.findAllByStore(store);
        return menus.stream().map(menu -> new MenuResponseDto(menu.getMenu_id(), menu.getName(), menu.getDescription(), menu.getPrice(), menu.getImg_path())).toList();
    }

    /**
     * Store 객체 가져오며 예외처리
     */
    private Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("Store with id " + storeId + " not found"));
    }
}