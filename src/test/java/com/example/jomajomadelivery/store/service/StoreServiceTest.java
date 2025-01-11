package com.example.jomajomadelivery.store.service;

import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.dto.request.UpdateStoreRequestDto;
import com.example.jomajomadelivery.store.dto.response.StoreResponseDto;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void 준비() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 가게추가_성공() {
        // Given: 가게를 추가할 요청 데이터 준비
        StoreRequestDto storeRequestDto = new StoreRequestDto(
                "KOREAN", "Example Store", "A great place to eat", "123 Main St", "123-456-7890",
                "some_path.jpg", LocalTime.of(9, 0), LocalTime.of(21, 0), 10000, 2000
        );
        User user = User.builder().userId(1L).name("Test User").build();
        Store expectedStore = Store.builder()
                .user(user)
                .category(Category.valueOf(storeRequestDto.category()))
                .name(storeRequestDto.name())
                .description(storeRequestDto.description())
                .address(storeRequestDto.address())
                .phoneNumber(storeRequestDto.phoneNumber())
                .imgPath(storeRequestDto.imgPath())
                .openTime(storeRequestDto.openTime())
                .closeTime(storeRequestDto.closeTime())
                .rating(0.0)
                .minOrderPrice(storeRequestDto.minOrderPrice())
                .deliveryPrice(storeRequestDto.deliveryPrice())
                .favoriteCount(0L)
                .isDeleted(false)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(storeRepository.save(any(Store.class))).thenReturn(expectedStore);

        // When: 가게 추가 서비스 호출
        assertDoesNotThrow(() -> storeService.addStore(storeRequestDto));

        // Then: 가게 생성 검증
        verify(userRepository, times(1)).save(any(User.class));
        verify(storeRepository, times(1)).save(any(Store.class));
    }

    @Test
    void 모든가게조회_성공() {
        // Given: 페이징 요청 준비 및 가게 리스트 설정
        Pageable pageable = PageRequest.of(0, 10);
        Store store = mock(Store.class);
        Page<Store> storePage = new PageImpl<>(List.of(store));
        when(storeRepository.findAll(pageable)).thenReturn(storePage);

        // When: 모든 가게 조회 서비스 호출
        Page<StoreResponseDto> result = storeService.findAllStore(pageable);

        // Then: 조회 결과 검증
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(storeRepository, times(1)).findAll(pageable);
    }

    @Test
    void 가게수정_성공() {
        // Given: 수정할 가게 ID와 수정 요청 데이터 준비
        Long storeId = 1L;
        UpdateStoreRequestDto dto = new UpdateStoreRequestDto(
                "Updated description", "Updated image path",
                LocalTime.of(10, 0), LocalTime.of(22, 0), 15000, 3000
        );
        Store mockStore = mock(Store.class);
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(mockStore));

        // When: 가게 수정 서비스 호출
        StoreResponseDto result = storeService.updateStore(storeId, dto);

        // Then: 수정 결과 검증
        assertNotNull(result);
        assertEquals("Updated description", result.description());

        verify(storeRepository, times(1)).findById(storeId);
        verify(mockStore, times(1)).updateStore(dto);
    }

    @Test
    void 가게수정_가게없음_예외발생() {
        // Given: 존재하지 않는 가게 ID와 수정 요청 데이터 준비
        Long storeId = 1L;
        UpdateStoreRequestDto updateStoreRequestDto = new UpdateStoreRequestDto(
                null, null, null, null, 0, 0
        );
        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        // When: 가게 수정 서비스 호출
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> storeService.updateStore(storeId, updateStoreRequestDto));

        // Then: 예외 발생 검증
        assertEquals("404 NOT_FOUND \"없슈\"", exception.getMessage());
        verify(storeRepository, times(1)).findById(storeId);
    }

    @Test
    void 가게영업중지_성공() {
        // Given: 영업 중지할 가게 데이터 준비
        Long storeId = 1L;
        Store mockStore = mock(Store.class);
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(mockStore));

        // When: 영업 중지 서비스 호출
        assertDoesNotThrow(() -> storeService.shutDownStore(storeId));

        // Then: 호출 및 상태 변경 검증
        verify(storeRepository, times(1)).findById(storeId);
        verify(mockStore, times(1)).shutDownStore();
    }
}