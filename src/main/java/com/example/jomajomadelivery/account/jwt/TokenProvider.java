package com.example.jomajomadelivery.account.auth.jwt;

import com.example.jomajomadelivery.user.entity.Role;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Component
public class TokenProvider {

    private final SecretKey key;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60; // 토큰 유효 시간: 1시간

    public TokenProvider(@Value("${spring.jwt.secret-key}") String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String createToken(Long id, Role role) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("roles", role);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALID_TIME))
                .signWith(key)
                .compact();
    }

    public Long getUserId(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    public Role getRole(String token) {
        return  Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", Role.class);
    }

    public Boolean isExpired(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return false;
        } catch (JwtException e) {
            log.info("token 만료");
            return true;
        }
    }
}