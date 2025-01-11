package com.example.jomajomadelivery.user.domain;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Email {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String emailText;

    private Email(String email) {
        this.emailText = email;
    }

    /**
     * 입력받은 email 을 검증 후 Email 객체 생성
     */
    public static Email generateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        if (!validateEmail(email)) {
            throw new IllegalArgumentException("이메일 형식이 맞지 않습니다.");
        }

        return new Email(email);
    }

    /**
     * 이메일 형식 검증
     */
    private static boolean validateEmail(String email) {
        return pattern.matcher(email).matches();
    }
}
