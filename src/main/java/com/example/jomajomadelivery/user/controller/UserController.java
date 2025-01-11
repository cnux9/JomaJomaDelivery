package com.example.jomajomadelivery.user.controller;

import com.example.jomajomadelivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> findUserById() {
//        userService.findById(userId);

        return ResponseEntity.ok("");
    }

    @PostMapping
    public ResponseEntity<String> updateUser() {
//        userService.updateUser()

        return ResponseEntity.ok("");
    }
}
