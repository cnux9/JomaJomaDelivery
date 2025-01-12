package com.example.jomajomadelivery.user.entity;

import com.example.jomajomadelivery.account.auth.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.account.oauth2.service.SocialProvider;
import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.user.dto.request.UserUpdateDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialProvider socialType;

    private String providerId;

    @Column(nullable = false)
    private String email;

    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    public static User createUser(SignUpUserDto dto, String email, String password) {
        return User.builder()
                .socialType(dto.socialType())
                .providerId(dto.providerId())
                .email(email)
                .password(password)
                .name(dto.name())
                .nickName(dto.nickName())
                .phoneNumber(dto.phoneNumber())
                // 주소 입력 필요
                .role(dto.role())
                .isDeleted(false)
                .build();
    }

    public User updateUser(UserUpdateDto dto) {
        this.email = dto.email();
        this.password = dto.password();
        this.name = dto.name();
        this.nickName = dto.nickname();
        this.phoneNumber = dto.phoneNumber();
        return this;
    }
}
