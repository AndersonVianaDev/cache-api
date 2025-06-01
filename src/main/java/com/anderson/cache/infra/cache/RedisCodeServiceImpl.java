package com.anderson.cache.infra.cache;

import com.anderson.cache.domain.services.IRedisCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisCodeServiceImpl implements IRedisCodeService {
    private static final long EXPIRATION_MINUTES = 15;

    private final StringRedisTemplate redisTemplate;

    @Override
    public void save(String email, String code) {
        redisTemplate.opsForValue().set(email, code, Duration.ofMinutes(EXPIRATION_MINUTES));
    }


    @Override
    public String get(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    @Override
    public void delete(String email) {
        redisTemplate.delete(email);
    }
}
