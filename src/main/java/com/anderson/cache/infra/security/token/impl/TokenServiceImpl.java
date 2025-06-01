package com.anderson.cache.infra.security.token.impl;

import com.anderson.cache.domain.model.User;
import com.anderson.cache.infra.security.token.ITokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

@Service
public class TokenServiceImpl implements ITokenService {

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.expiration}")
    private String expiration;

    @Override
    public String generate(User user) {
        try {
            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(new Date(new Date().getTime() + Long.parseLong(expiration)))
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long extractId(String token) {
        try {
            String subject = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();

            return Long.parseLong(subject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
