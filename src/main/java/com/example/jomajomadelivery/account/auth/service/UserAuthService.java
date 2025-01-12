package com.example.jomajomadelivery.account.auth.service;

import com.example.jomajomadelivery.account.auth.dto.request.LoginUserDto;
import com.example.jomajomadelivery.account.auth.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.account.auth.repository.UserAuthRepository;
import com.example.jomajomadelivery.account.domain.Email;
import com.example.jomajomadelivery.account.domain.Password;
import com.example.jomajomadelivery.account.exception.EmailErrorCode;
import com.example.jomajomadelivery.account.exception.LoginErrorCode;
import com.example.jomajomadelivery.account.jwt.TokenProvider;
import com.example.jomajomadelivery.account.oauth2.service.SocialProvider;
import com.example.jomajomadelivery.address.dto.request.AddressRequestDto;
import com.example.jomajomadelivery.address.service.AddressService;
import com.example.jomajomadelivery.exception.CustomException;
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

    private final AddressService addressService;

    private final TokenProvider tokenProvider;

    public Optional<User> findBySocialTypeAndProviderId(SocialProvider socialProvider, String providerId) {
        return userAuthRepository.findBySocialTypeAndProviderId(socialProvider, providerId);
    }

    @Transactional
    public void registerUser(SignUpUserDto dto) {
        Long addressId = registerAddress(dto);

        if (dto.socialType() == null) {
            String stringPassword = Password.validatePassword(dto.password())
                    .generateEncryptedPassword()
                    .getStringPassword();

            String stringEmail = Email.generateEmail(dto.email())
                    .getEmailText();

            userAuthRepository.save(User.createUser(dto, stringEmail, stringPassword, addressId));
            return;
        }

        userAuthRepository.save(User.createUser(dto, dto.email(), dto.password(), addressId));
    }

    public Cookie authenticateUser(LoginUserDto dto) {
        User user = userAuthRepository.findByEmailAndSocialTypeIsNull(dto.email())
                .orElseThrow(() -> new CustomException(LoginErrorCode.INVALID_CREDENTIALS));

        if (!Password.generatePassword(user.getPassword()).matchPassword(dto.password())) {
            throw new CustomException(LoginErrorCode.INVALID_CREDENTIALS);
        }

        return createCookie(user);
    }

    public void emailDuplicate(String email) {
        if (userAuthRepository.findByEmailAndSocialTypeIsNull(email).isPresent()) {
            throw new CustomException(EmailErrorCode.EMAIL_ALREADY_USED);
        }
    }

    private Long registerAddress(SignUpUserDto dto) {
        AddressRequestDto addressRequestDto = new AddressRequestDto(
                dto.name(),
                dto.zipcode(),
                dto.state(),
                dto.city(),
                dto.street(),
                dto.detailAddress()
        );
        return addressService.create(addressRequestDto).addressId();
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
