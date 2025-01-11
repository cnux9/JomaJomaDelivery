package com.example.jomajomadelivery.menu.service;

import com.example.jomajomadelivery.menu.dto.request.MenuRequestDto;
import com.example.jomajomadelivery.menu.dto.response.MenuResponseDto;
import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.menu.repository.MenuRepository;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.util.DumpDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    void 메뉴_생성_성공() {
        // Given
        Store mockStore = DumpDataFactory.store();
        Menu mockMenu = DumpDataFactory.menu(mockStore);

        MenuRequestDto menuRequestDto = new MenuRequestDto("햄부기", "강태공이 낚은 햄부기", 10000, "some_img");
        MenuResponseDto expected = new MenuResponseDto(null, "햄부기", "강태공이 낚은 햄부기", 10000, "some_img");

        when(menuRepository.save(any(Menu.class))).thenReturn(mockMenu);
        when(storeRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(mockStore));

        // When
        MenuResponseDto result = menuService.createMenu(1L, menuRequestDto);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void 메뉴_조회_성공() {
        // Given
        Store mockStore = DumpDataFactory.store();

        MenuResponseDto expected1 = new MenuResponseDto(null, "강피자", "강태공이 버린 피자", 15000, "path1");
        MenuResponseDto expected2 = new MenuResponseDto(null, "강버거", "강태공이 주운 버거", 10000, "path2");

        when(storeRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(mockStore));
        List<Menu> menus = List.of(
                Menu.builder().store(mockStore).name("강피자").description("강태공이 버린 피자").price(15000).imgPath("path1").build(),
                Menu.builder().store(mockStore).name("강버거").description("강태공이 주운 버거").price(10000).imgPath("path2").build()
        );
        when(menuRepository.findAllByStore(mockStore)).thenReturn(menus);

        // When
        List<MenuResponseDto> result = menuService.getMenus(1L);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected1);
        assertThat(result.get(1))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected2);
    }

    @Test
    void 메뉴_단건_조회_성공() { // 특정 메뉴를 조회하는 테스트
        // Given
        Store mockStore = DumpDataFactory.store();
        Menu mockMenu = DumpDataFactory.menu(mockStore);

        MenuRequestDto expected = new MenuRequestDto("햄부기", "강태공이 낚은 햄부기", 10000, "some_img");

        when(menuRepository.findById(1L)).thenReturn(Optional.of(mockMenu));

        // When
        MenuResponseDto result = menuService.getMenu(1L);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void 메뉴_수정_성공() {
        // Given
        Store mockStore = DumpDataFactory.store();
        Menu mockMenu = DumpDataFactory.menu(mockStore);

        MenuRequestDto expected = new MenuRequestDto("피자강", "강태공이 주운 피자", 12000, "some_img");
        MenuRequestDto updateRequest = new MenuRequestDto("피자강", "강태공이 주운 피자", 12000, "some_img");

        when(menuRepository.findById(1L)).thenReturn(Optional.of(mockMenu));
        when(menuRepository.save(any(Menu.class))).thenReturn(mockMenu);

        // When
        MenuResponseDto result = menuService.updateMenu(1L, updateRequest);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    void 메뉴_삭제_성공() {
        // Given
        Store mockStore = DumpDataFactory.store();
        Menu mockMenu = DumpDataFactory.menu(mockStore);
        when(menuRepository.findById(1L)).thenReturn(Optional.of(mockMenu));

        // When
        menuService.deleteMenu(1L);

        // Then
        verify(menuRepository, times(1)).delete(mockMenu);
    }
}