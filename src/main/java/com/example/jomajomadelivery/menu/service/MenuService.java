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

import static com.example.jomajomadelivery.menu.entity.Menu.newMenu;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    //Todo:: 사장님 권한 확인 필요
    public MenuResponseDto createMenu(Long storeId, MenuRequestDto menuRequestDto) {
        Store store = getStoreEntity(storeId);
        Menu menu = newMenu(store, menuRequestDto);
        Menu savedMenu = menuRepository.save(menu);
        return new MenuResponseDto(savedMenu.getMenu_id(), savedMenu.getName(), savedMenu.getDescription(), savedMenu.getPrice(), savedMenu.getImg_path());
    }

    public List<MenuResponseDto> getMenus(Long storeId) {
        Store store = getStoreEntity(storeId);
        List<Menu> menus = menuRepository.findAllByStore(store);
        return menus.stream().map(menu -> new MenuResponseDto(menu.getMenu_id(), menu.getName(), menu.getDescription(), menu.getPrice(), menu.getImg_path())).toList();
    }

    public MenuResponseDto getMenu(Long menuId) {
        Menu menu = getMenuEntity(menuId);
        return new MenuResponseDto(menu.getMenu_id(), menu.getName(), menu.getDescription(), menu.getPrice(), menu.getImg_path());
    }

    //Todo:: 사장님 권한 확인 필요
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto) {
        Menu menu = getMenuEntity(menuId);
        menu.updateMenu(menuRequestDto);
        Menu savedMenu = menuRepository.save(menu);
        return new MenuResponseDto(savedMenu.getMenu_id(), savedMenu.getName(), savedMenu.getDescription(), savedMenu.getPrice(), savedMenu.getImg_path());
    }

    //Todo:: 사장님 권한 확인 필요
    public void deleteMenu(Long menuId) {
        Menu menu = getMenuEntity(menuId);
        menuRepository.delete(menu);
    }

    /**
     * Store 객체 가져오며 예외처리
     */
    private Store getStoreEntity(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("Store with id " + storeId + " not found"));
    }

    /**
     * Menu 객체 가져오며 예외처리
     */
    private Menu getMenuEntity(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("Store with id " + menuId + " not found"));
    }
}