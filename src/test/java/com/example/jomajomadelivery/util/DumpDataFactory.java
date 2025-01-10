package com.example.jomajomadelivery.util;

import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.store.entity.Category;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.user.entity.Role;
import com.example.jomajomadelivery.user.entity.User;

import java.time.LocalTime;

public class DumpDataFactory {

    public static Review review() {
        return review(store());
    }

    public static Review review(Store store) {
        return Review.builder()
//                .user(user)
                .userId(1L)
                .store(store)
//                .storeId(storeId)
                .contents("머리카락이 나왔어요.")
                .imgPath("someImg.jpg")
                .rating(5)
                .build();
    }

    public static User user() {
        return User.builder()
                .email("abc@naver.com")
                .password("1111")
                .name("조훈")
                .role(Role.ROLE_USER)
                .nickName("홍박사")
                .phoneNumber("010-0000-0000")
                .isDeleted(false)
                .build();
    }

    public static Store store() {
        return store(user());
    }

    public static Store store(User user) {
        return Store.builder()
                .storeId(1L)
                .user(user)
                .category(Category.JAPANESE)
                .name("파파스터치")
                .description("300년 전통의 가부장적인 맛")
                .address("충주시 사과군")
                .phoneNumber("010-0000-0000")
                .imgPath("someImg.jpg")
                .openTime(LocalTime.NOON)
                .closeTime(LocalTime.MIDNIGHT)
                .minOrderPrice(32100)
                .deliveryPrice(2500)
                .favoriteCount(0L)
                .rating(0.0)
                .isDeleted(false)
                .build();
    }

    public static Menu menu(Store store) {
        return Menu.builder()
                .menu_id(1L)
                .store(store)
                .name("햄부기")
                .description("강태공이 낚은 햄부기")
                .price(10000)
                .img_path("some_img")
                .build();
    }
}
