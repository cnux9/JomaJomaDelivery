package com.example.jomajomadelivery.address.service;

import com.example.jomajomadelivery.address.dto.request.AddressRequestDto;
import com.example.jomajomadelivery.address.dto.response.AddressResponseDto;
import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.address.entity.EntityType;
import com.example.jomajomadelivery.address.repository.AddressRepository;
import com.example.jomajomadelivery.util.DumpDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    AddressRepository addressRepository;

    @InjectMocks
    AddressService addressService;

    @Test
    void 주소_생성() {

        // Given
        Address mockReturnAddress = DumpDataFactory.address();

        AddressRequestDto requestDto = new AddressRequestDto(EntityType.USER, 1L, "친구집", "335-003", "경기도", "하남시", "위례중앙로", "아파트아파트 5동 5호");
        AddressResponseDto expected = new AddressResponseDto(1L, EntityType.USER, 1L, "친구집", "335-003", "경기도", "하남시", "위례중앙로", "아파트아파트 5동 5호", LocalDateTime.parse("2025-01-09T18:21:44.44634"));

        when(addressRepository.save(any(Address.class))).thenReturn(mockReturnAddress);

        // When
        AddressResponseDto result = addressService.create(requestDto);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("addressId", "createdAt")
                .isEqualTo(expected);
    }

    @Test
    void 주소_단건_조회() {

        // Given
        Address mockReturnAddress = DumpDataFactory.address();

        AddressResponseDto expected = new AddressResponseDto(1L, EntityType.USER, 1L, "친구집", "335-003", "경기도", "하남시", "위례중앙로", "아파트아파트 5동 5호", LocalDateTime.parse("2025-01-09T18:21:44.44634"));

        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(mockReturnAddress));

        // When
        AddressResponseDto result = addressService.findById(1L);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("addressId", "createdAt")
                .isEqualTo(expected);
    }

//    @Test
//    void 주소_다건_조회() {
//
//        // Given
//        Review mockReturnReview = DumpDataFactory.review();
//
//        ReviewResponseDto expected = new ReviewResponseDto(1L, 1L, 1L, "머리카락이 나왔어요.", 5, "someImg.jpg", LocalDateTime.parse("2025-01-09T18:21:44.44634"));
//
//        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(mockReturnReview));
//
//        // When
//        ReviewResponseDto result = reviewService.findById(1L);
//
//        // Then
//        assertThat(result)
//                .usingRecursiveComparison()
//                .ignoringFields("reviewId", "createdAt", "storeId")
//                .isEqualTo(expected);
//    }

    @Test
    void 주소_수정() {

        // Given
        Address mockReturnFoundAddress = DumpDataFactory.address();
        Address mockReturnSavedAddress = DumpDataFactory.addressOther();

        when(addressRepository.findById(any(Long.class))).thenReturn(Optional.of(mockReturnFoundAddress));
        when(addressRepository.save(any(Address.class))).thenReturn(mockReturnSavedAddress);

        String NAME = "르탄이집";
        String ZIPCODE = "111-222";
        String STATE = "충청도";
        String CITY = "충주시";
        String STREET = "사과로";
        String DETAILED_ADDRESS = "111-1";

        AddressRequestDto requestDto = new AddressRequestDto(EntityType.USER, 1L, NAME, ZIPCODE, STATE, CITY, STREET, DETAILED_ADDRESS);
        AddressResponseDto expected = new AddressResponseDto(1L, EntityType.USER, 1L, NAME, ZIPCODE, STATE, CITY, STREET, DETAILED_ADDRESS, LocalDateTime.parse("2025-01-09T18:21:44.44634"));

        // When
        AddressResponseDto result = addressService.update(1L, requestDto);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("addressId", "createdAt")
                .isEqualTo(expected);
    }
}