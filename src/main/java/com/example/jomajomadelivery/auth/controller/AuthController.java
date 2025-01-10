package com.example.jomajomadelivery.auth.controller;

import com.example.jomajomadelivery.auth.dto.request.SignUpUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // Todo: 일반 로그인/회원가입 담당

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public String loginProcess(@RequestParam String username, @RequestParam String password) {
//        로그인 로직
//        실패하면 다시 로그인 화면으로 리다이렉트
//        홈화면 리다이렉트
        return "redirect:/home";
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("userSignUp", SignUpUserDto.empty());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signupProcess() {
        return "redirect:/login";
    }
}
