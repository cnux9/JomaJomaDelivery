package com.example.jomajomadelivery.address.entity;


import com.example.jomajomadelivery.address.dto.request.AddressRequestDto;
import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "addresses")
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Enumerated(EnumType.STRING)
    private EntityType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
//    private Store
    private Long entityId;

    private String name;

    private String zipcode;
    private String state;
    private String city;
    private String street;

    @Column(name = "detailed_address")
    private String detailedAddress;

    @Column(name = "is_active")
    private Boolean isActive;

    public static Address createAddress(AddressRequestDto dto) {
        return Address.builder()
                .type(dto.type())
                .entityId(dto.entityId())
                .name(dto.name())
                .zipcode(dto.zipcode())
                .state(dto.state())
                .city(dto.city())
                .street(dto.street())
                .detailedAddress(dto.detailedAddress())
                .build();
    }

    // TODO: 변수명 isDelete? 혹은 직관적인 다른 걸로 변경?
    public void softDelete() {
        this.isActive = false;
    }

    public Address getUpdatedAddress(AddressRequestDto dto) {
        // dto에서 널값인 경우 기존의 값을 사용
        return Address.builder()
            .type(coalesce(dto.type(), type))
            .entityId(coalesce(dto.entityId(), entityId))
            .name(coalesce(dto.name(), name))
            .zipcode(coalesce(dto.zipcode(), zipcode))
            .state(coalesce(dto.state(), state))
            .city(coalesce(dto.city(), city))
            .street(coalesce(dto.street(), street))
            .detailedAddress(coalesce(dto.detailedAddress(), detailedAddress))
            .build();
    }

    private <T> T coalesce(T var1, T var2) {
        return var1 == null ? var2 : var1;
    }

    public String toString() {
        return zipcode + state + city + street + detailedAddress;
    }
}
