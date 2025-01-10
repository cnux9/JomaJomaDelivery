package com.example.jomajomadelivery.address.dto.response;

import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.address.entity.EntityType;

import java.time.LocalDateTime;

public record AddressResponseDto(
        Long addressId,
        EntityType type,
        Long entityId,
        String name,
        String zipcode,
        String state,
        String city,
        String street,
        String detailedAddress,
        LocalDateTime creaetdAt
) {
    public static AddressResponseDto toDto(Address address) {
        return new AddressResponseDto(
                address.getAddressId(),
                address.getType(),
                address.getEntityId(),
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
