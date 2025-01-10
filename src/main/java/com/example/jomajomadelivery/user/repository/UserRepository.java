package com.example.jomajomadelivery.user.repository;

import com.example.jomajomadelivery.auth.oauth.SocialProvider;
import com.example.jomajomadelivery.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialTypeAndProviderId(SocialProvider socialType, String providerId);

    Optional<User> findByEmail(String email);
}
