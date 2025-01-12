package com.example.jomajomadelivery.store.entity;

public enum Category {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    PIZZA("피자"),
    FAST_FOOD("패스트푸드"),
    CAFE("카페");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
