package com.example.jomajomadelivery.account.oauth2.controller;

import com.example.jomajomadelivery.account.auth.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.account.oauth2.service.SocialProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SocialAuthController {

    /**
     * 소셜로그인 추가 정보 입력 가입 페이지
     */
    @GetMapping("/signup/additional-info")
    public String additionalInfoPage(@RequestParam String name,
                                     @RequestParam String email,
                                     @RequestParam SocialProvider socialType,
                                     @RequestParam String providerId,
                                     Model model
    ) {
        SignUpUserDto signUpDto = SignUpUserDto.createFromSocialLoginDto(name, email, socialType, providerId);

        model.addAttribute("userSignUp", signUpDto);
        model.addAttribute("socialType", socialType);
        model.addAttribute("providerId", providerId);
        model.addAttribute("isSocial", true);
        return "account/signup";
    }
}
