package com.example.jomajomadelivery.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private Role role;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "created_date_time", nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    // 기본 생성자
    public User() {
    }

    // 모든 필드를 포함하는 생성자 (필요에 따라 추가)
    public User(String email, String password, String name, String address, Role role, String nickname, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.role = role;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    // Getter 및 Setter 메서드

    public Long getUserId() {
        return userId;
    }

    // userId는 자동 생성되므로 Setter는 생략할 수 있습니다.

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // 엔티티 생명주기 이벤트를 사용하여 생성 및 수정 시각 자동 설정
    @PrePersist
    protected void onCreate() {
        this.createdDateTime = LocalDateTime.now();
        this.isDeleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDateTime = LocalDateTime.now();
    }
}