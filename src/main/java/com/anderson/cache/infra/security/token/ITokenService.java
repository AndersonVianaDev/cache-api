package com.anderson.cache.infra.security.token;

import com.anderson.cache.domain.model.User;

public interface ITokenService {

    String generate(User user);
    Long extractId(String token);
}
