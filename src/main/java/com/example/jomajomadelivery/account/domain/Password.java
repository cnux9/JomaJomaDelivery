package com.example.jomajomadelivery.account.domain;

import com.example.jomajomadelivery.account.exception.PasswordErrorCode;
import com.example.jomajomadelivery.account.util.BCryptUtil;
import com.example.jomajomadelivery.exception.CustomException;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Password {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    private final String stringPassword;

    private Password(String encryptedPassword) {
        if (encryptedPassword == null || encryptedPassword.isEmpty()) {
            throw new CustomException(PasswordErrorCode.PASSWORD_EMPTY);
        }

        this.stringPassword = encryptedPassword;
    }

    /**
     * 입력받은 비밀번호를 암호화 후 Password 객체 생성
     */
    public Password generateEncryptedPassword() {
        return new Password(BCryptUtil.encrypt(stringPassword));
    }

    /**
     * 이미 암호화된 비밀번호로 Password 객체 생성
     */
    public static Password generatePassword(String encryptedPassword) {
        return new Password(encryptedPassword);
    }

    /**
     * 비밀번호 형식 검증
     */
    public static Password validatePassword(String password) {
        if (!pattern.matcher(password).matches()) {
            throw new CustomException(PasswordErrorCode.PASSWORD_INVALID_FORMAT);
        }
        return new Password(password);
    }

    /**
     * 입력된 비밀번호가 저장된 비밀번호와 일치하는지 검증
     */
    public boolean matchPassword(String rawPassword) {
        return BCryptUtil.matches(rawPassword, stringPassword);
    }
}
