package com.example.jomajomadelivery.menu.service;

import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public void createMenu(Long storeId, MenuRequestDto menuRequestDto) {
        //Todo: storeId에 해당하는 Store 가져오기
        Menu menu = new Menu(store, menuRequestDto.getName(), menuRequestDto.getDescription(), menuRequestDto.getPrice(), menuRequestDto.getImg_path());
        Menu savedMenu = menuRepository.save(menu);
    }
}
