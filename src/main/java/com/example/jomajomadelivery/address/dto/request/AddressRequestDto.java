package com.example.jomajomadelivery.address.dto.request;

public record AddressRequestDto(
        String name,
        String zipcode,
        String state,
        String city,
        String street,
        String detailedAddress
) {
}
