package com.anderson.cache.controller.dtos.response;

import com.anderson.cache.domain.model.User;

public record UserResponseDTO(Long id, String name, String email) {
    public static UserResponseDTO of(User user) {
        return new UserResponseDTO(user.getId(), user.getName(),user.getEmail());
    }
}
