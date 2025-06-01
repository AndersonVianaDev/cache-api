package com.anderson.cache.domain.services;

public interface IPasswordResetService {
    void sendResetCode(String email);
    void resetPassword(String email, String code, String newPassword);
}
