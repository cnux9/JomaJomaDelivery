package com.example.jomajomadelivery.address.entity;


import com.example.jomajomadelivery.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "addresses")
@NoArgsConstructor
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EntityType type;
//    private User
//    private Store

    private String name;

    private String zipcode;
    private String state;
    private String city;
    private String street;

    @Column(name = "detailed_address")
    private String detailedAddress;

    @Column(name = "is_active")
    private Boolean isActive;

}
