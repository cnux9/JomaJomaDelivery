package com.example.jomajomadelivery.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    // Todo: 일반 로그인/회원가입 담당

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/login")
    public ModelAndView loginProcess(@RequestParam String username, @RequestParam String password) {
        return new ModelAndView();
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }
}
