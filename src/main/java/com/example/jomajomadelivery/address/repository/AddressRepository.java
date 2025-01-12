package com.example.jomajomadelivery.address.repository;


import com.example.jomajomadelivery.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
