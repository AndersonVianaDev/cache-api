package com.anderson.cache.controller;

import com.anderson.cache.controller.dtos.request.ForgotPasswordRequestDTO;
import com.anderson.cache.controller.dtos.request.LoginRequestDTO;
import com.anderson.cache.controller.dtos.request.ResetPasswordRequestDTO;
import com.anderson.cache.domain.model.User;
import com.anderson.cache.domain.services.IPasswordResetService;
import com.anderson.cache.infra.security.token.ITokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final ITokenService tokenService;
    private final IPasswordResetService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request) {
        final var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        final User user = (User) authenticationManager.authenticate(usernamePassword).getPrincipal();
        final String token = tokenService.generate(user);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/send-reset-code")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        service.sendResetCode(request.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        service.resetPassword(request.email(), request.code(), request.newPassword());
        return ResponseEntity.ok().build();
    }
}
