package com.example.jomajomadelivery.auth.repository;

import com.example.jomajomadelivery.auth.oauth2.SocialProvider;
import com.example.jomajomadelivery.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialTypeAndProviderId(SocialProvider socialType, String providerId);

    Optional<User> findByEmail(String email);
}