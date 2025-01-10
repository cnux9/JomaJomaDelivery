package com.example.jomajomadelivery.user.entity;

import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.auth.oauth2.SocialProvider;
import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.auth.dto.request.SignUpUserDto;
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

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String nickName;

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

    public static User createUser(SignUpUserDto dto) {
        return User.builder()
                .email(dto.email())
                .password(dto.password())
                .name(dto.name())
                .role(Role.ROLE_USER)
                .nickName(dto.nickName())
                .phoneNumber(dto.phoneNumber())
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
