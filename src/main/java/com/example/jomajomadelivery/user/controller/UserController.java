package com.example.jomajomadelivery.user.controller;

import com.example.jomajomadelivery.common.aop.account.CurrentUserId;
import com.example.jomajomadelivery.user.dto.request.UserUpdateDto;
import com.example.jomajomadelivery.user.entity.RoleConstants;
import com.example.jomajomadelivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Secured(RoleConstants.ROLE_USER)
public class UserController {
    
    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> findUserById(@CurrentUserId Long userId) {
        userService.findById(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("create user");
    }

    @PostMapping
    public ResponseEntity<String> updateUser(@CurrentUserId Long userid, UserUpdateDto dto) {
        userService.updateUser(userid, dto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("update user");
    }
}
