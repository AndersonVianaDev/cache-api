package com.anderson.cache.controller.dtos.request;

public record ResetPasswordRequestDTO(String email, String code, String newPassword) {
}
