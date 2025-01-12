package com.example.jomajomadelivery.address.dto.response;

import com.example.jomajomadelivery.address.entity.Address;

import java.time.LocalDateTime;

public record AddressResponseDto(
        Long addressId,
        String name,

        String zipcode,
        String state,
        String city,
        String street,
        String detailedAddress,
        LocalDateTime createdAt
) {
    public static AddressResponseDto toDto(Address address) {
        return new AddressResponseDto(
                address.getAddressId(),
                address.getName(),
                address.getZipcode(),
                address.getState(),
                address.getCity(),
                address.getStreet(),
                address.getDetailedAddress(),
                address.getCreatedAt()
        );
    }
}
