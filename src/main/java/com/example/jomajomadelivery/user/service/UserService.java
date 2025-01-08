package com.example.jomajomadelivery.user.service;

import com.example.jomajomadelivery.user.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(SignUpUserDto dto) {
        User user = User.createUser(dto);

        // Todo: Address 생성 및 적용
        userRepository.save(user);
    }
}
