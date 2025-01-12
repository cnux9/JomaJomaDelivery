package com.example.jomajomadelivery.account.auth.controller;

import com.example.jomajomadelivery.account.auth.dto.request.LoginUserDto;
import com.example.jomajomadelivery.account.auth.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.account.auth.service.UserAuthService;
import com.example.jomajomadelivery.account.exception.EmailErrorCode;
import com.example.jomajomadelivery.account.exception.PasswordErrorCode;
import com.example.jomajomadelivery.exception.CustomException;
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
        return "account/login";
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
        } catch (CustomException ex) {
            model.addAttribute("loginUserForm", LoginUserDto.empty());
            model.addAttribute("errorMessage", ex.getErrorCode().getMessage());
            return "account/login";
        }
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("userSignUp", SignUpUserDto.empty());
        model.addAttribute("isSocial", false);
        return "account/signup";
    }

    /**
     * 회원가입 처리
     */
    @PostMapping("/signup")
    public String signupProcess(SignUpUserDto dto, Model model) {
        log.info("회원가입 폼: {}", dto.toString());

        try {
            userAuthService.emailDuplicate(dto.email());
            userAuthService.registerUser(dto);
            return "redirect:/login";
        } catch (CustomException ex) {
            if (ex.getErrorCode() instanceof EmailErrorCode emailErrorCode) {
                if (emailErrorCode == EmailErrorCode.EMAIL_ALREADY_USED) {
                    model.addAttribute("emailErrorMessage", emailErrorCode.getMessage());
                }

                if (emailErrorCode == EmailErrorCode.EMAIL_INVALID_FORMAT) {
                    model.addAttribute("emailErrorMessage", emailErrorCode.getMessage());
                }
            } else if (ex.getErrorCode() instanceof PasswordErrorCode passwordErrorCode &&
                    passwordErrorCode == PasswordErrorCode.PASSWORD_INVALID_FORMAT
            ) {
                model.addAttribute("passwordErrorMessage", passwordErrorCode.getMessage());
            }

            model.addAttribute("userSignUp", dto);
            model.addAttribute("isSocial", false);
            return "account/signup";
        }
    }
}
