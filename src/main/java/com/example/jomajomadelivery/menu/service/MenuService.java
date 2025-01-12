package com.example.jomajomadelivery.menu.service;

import com.example.jomajomadelivery.common.ImageHandler;
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
    private final ImageHandler imageHandler;

    //Todo:: 사장님 권한 확인 필요
    public MenuResponseDto createMenu(Long storeId, MenuRequestDto menuRequestDto) {
        Store store = getStoreEntity(storeId);
        String imgPath = imageHandler.save(menuRequestDto.img(), "menu");
        Menu menu = newMenu(store, menuRequestDto, imgPath);
        Menu savedMenu = menuRepository.save(menu);
        return MenuResponseDto.toDto(savedMenu);
    }

    public List<MenuResponseDto> getMenus(Long storeId) {
        Store store = getStoreEntity(storeId);
        List<Menu> menus = menuRepository.findAllByStore(store);
        return menus.stream().map(menu -> new MenuResponseDto(menu.getMenuId(), menu.getName(), menu.getDescription()
                , menu.getPrice(), menu.getImgPath())).toList();
    }

    public MenuResponseDto getMenu(Long menuId) {
        Menu menu = getMenuEntity(menuId);
        return MenuResponseDto.toDto(menu);
    }

    //Todo:: 사장님 권한 확인 필요
    public MenuResponseDto updateMenu(Long menuId, MenuRequestDto menuRequestDto) {
        Menu menu = getMenuEntity(menuId);
        String imgPath = menu.getImgPath();
        if (menuRequestDto.img() != null) {
            imgPath = imageHandler.save(menuRequestDto.img(), "menu");
        }
        menu.updateMenu(menuRequestDto, imgPath);
        Menu savedMenu = menuRepository.save(menu);
        return MenuResponseDto.toDto(savedMenu);
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
    public Menu getMenuEntity(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("Menu with id " + menuId + " not found"));
    }
}