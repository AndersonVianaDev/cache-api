package com.anderson.cache.controller;

import com.anderson.cache.controller.dtos.request.UserRequestDTO;
import com.anderson.cache.controller.dtos.response.UserResponseDTO;
import com.anderson.cache.domain.model.User;
import com.anderson.cache.domain.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO request) {
        final User user = service.create(UserRequestDTO.from(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.of(user));
    }
}
