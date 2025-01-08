package com.example.jomajomadelivery.address.dto.request;

import com.example.jomajomadelivery.address.entity.EntityType;

public record AddressRequestDto(
        EntityType type,
        Long entityId,
        String name,

        String zipcode,
        String state,
        String city,
        String street,
        String detailedAddress
) {
}
