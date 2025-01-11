package com.example.jomajomadelivery.auth.service;

import com.example.jomajomadelivery.auth.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;

}
