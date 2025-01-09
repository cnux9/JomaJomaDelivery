package com.example.jomajomadelivery.orders.dto.response;

import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.orders.entity.Status;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrdersResponseDto {
    private Status status;
    private Address address;
}
