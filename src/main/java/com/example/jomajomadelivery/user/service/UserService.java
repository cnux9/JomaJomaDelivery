package com.example.jomajomadelivery.user.service;

import com.example.jomajomadelivery.auth.oauth.SocialProvider;
import com.example.jomajomadelivery.user.dto.request.SignUpUserDto;
import com.example.jomajomadelivery.user.dto.request.UserUpdateDto;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(SignUpUserDto dto) {
        User user = User.createUser(dto);

        // Todo: Address 생성 및 적용
        userRepository.save(user);
    }

    public Optional<User> findBySocialTypeAndProviderId(SocialProvider socialType, String providerId) {
        return userRepository.findBySocialTypeAndProviderId(socialType, providerId);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(""));
    }

    @Transactional
    public User updateUser(Long id, UserUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(""));

        return user.updateUser(dto);
    }
}
