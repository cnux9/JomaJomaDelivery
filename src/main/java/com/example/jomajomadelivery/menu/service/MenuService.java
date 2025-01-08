package com.example.jomajomadelivery.menu.service;

import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.menu.repository.MenuRepository;
import com.example.jomajomadelivery.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public void createMenu(Long storeId, MenuRequestDto menuRequestDto) {
        //Todo: storeId에 해당하는 Store 가져오기
      
//        Menu menu = new Menu(store, menuRequestDto.getName(), menuRequestDto.getDescription(), menuRequestDto.getPrice(), menuRequestDto.getImg_path());
//        Menu savedMenu = menuRepository.save(menu);
    }

    public List<MenuResponseDto> getMenues(Long storeId, MenuRequestDto menuRequestDto) {
        //Todo: storeId에 해당하는 Store 가져오기
        Store store = storeRepository.findbyId(storeId);
        List<Menu> menus = menuRepository.findAllByStore(store);
        return menus.stream().map(menu -> new MenuResponseDto(menu.getMenu_id(), menu.getName(), menu.getDescription(), menu.getPrice(), menu.getImgPath())).toList();
    }
}