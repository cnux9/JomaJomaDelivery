package com.example.jomajomadelivery.account.domain;

import com.example.jomajomadelivery.account.exception.EmailErrorCode;
import com.example.jomajomadelivery.exception.CustomException;
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
            throw new CustomException(EmailErrorCode.EMAIL_EMPTY);
        }

        if (!validateEmail(email)) {
            throw new CustomException(EmailErrorCode.EMAIL_INVALID_FORMAT);
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
