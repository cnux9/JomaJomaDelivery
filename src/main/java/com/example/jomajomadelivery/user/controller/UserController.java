package com.example.jomajomadelivery.user.controller;

import com.example.jomajomadelivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp() {

        // Todo: 회원가입은 소셜로그인을 통해 진행

        return ResponseEntity.ok("");
    }
}
