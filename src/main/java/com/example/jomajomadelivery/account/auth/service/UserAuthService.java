package com.example.jomajomadelivery.account.auth.service;

import com.example.jomajomadelivery.account.auth.dto.request.LoginUserDto;
import com.example.jomajomadelivery.account.auth.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.account.jwt.TokenProvider;
import com.example.jomajomadelivery.account.oauth2.service.SocialProvider;
import com.example.jomajomadelivery.account.auth.repository.UserAuthRepository;
import com.example.jomajomadelivery.user.domain.Email;
import com.example.jomajomadelivery.user.domain.Password;
import com.example.jomajomadelivery.user.entity.User;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;

    private final TokenProvider tokenProvider;

    public Optional<User> findBySocialTypeAndProviderId(SocialProvider socialProvider, String providerId) {
        return userAuthRepository.findBySocialTypeAndProviderId(socialProvider, providerId);
    }

    @Transactional
    public void registerUser(SignUpUserDto dto) {
        if (dto.socialType() == null) {
            String stringPassword = Password.validatePassword(dto.password())
                    .generateEncryptedPassword()
                    .getStringPassword();

            String stringEmail = Email.generateEmail(dto.email())
                    .getEmailText();

            userAuthRepository.save(User.createUser(dto, stringEmail, stringPassword));
            return;
        }

        userAuthRepository.save(User.createUser(dto, dto.email(), dto.password()));
    }

    public Cookie authenticateUser(LoginUserDto dto) {
        User user = userAuthRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("아이디(로그인 전화번호, 로그인 전용 아이디) 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요."));

        if (!Password.generatePassword(user.getPassword()).matchPassword(dto.password())) {
            throw new IllegalArgumentException("아이디(로그인 전화번호, 로그인 전용 아이디) 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");
        }

        return createCookie(user);
    }

    public void emailDuplicate(String email) {
        if (userAuthRepository.findByEmailAndSocialTypeIsNull(email).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }
    }

    private Cookie createCookie(User user) {
        String token = tokenProvider.createToken(user.getUserId(), user.getRole());

        Cookie jwtCookie = new Cookie("Authorization", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60 * 60);

        return jwtCookie;
    }
}
