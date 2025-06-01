package com.anderson.cache.domain.services;

public interface IRedisCodeService {
    void save(String email, String code);
    String get(String email);
    void delete(String email);
}
