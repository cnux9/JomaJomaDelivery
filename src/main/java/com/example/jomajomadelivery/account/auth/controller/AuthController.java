package com.example.jomajomadelivery.auth.controller;

import com.example.jomajomadelivery.auth.dto.request.LoginUserDto;
import com.example.jomajomadelivery.auth.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.auth.service.UserAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginUserForm", LoginUserDto.empty());
        return "auth/login";
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public String loginProcess(
            LoginUserDto dto,
            Model model,
            HttpServletResponse response
    ) {
        try {
            Cookie cookie = userAuthService.authenticateUser(dto);
            response.addCookie(cookie);
            return "redirect:/";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("loginUserForm", LoginUserDto.empty());
            model.addAttribute("errorMessage", ex.getMessage());
            return "auth/login";
        }
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("userSignUp", SignUpUserDto.empty());
        return "auth/signup";
    }

    /**
     * 회원가입 처리
     */
    @PostMapping("/signup")
    public String signupProcess(SignUpUserDto dto) {
        log.info("회원가입 폼: {}", dto.toString());
        userAuthService.registerUser(dto);
        return "redirect:/login";
    }
}
