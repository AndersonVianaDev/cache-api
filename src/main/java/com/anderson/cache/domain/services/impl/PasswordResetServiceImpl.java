package com.anderson.cache.domain.services.impl;

import com.anderson.cache.domain.model.User;
import com.anderson.cache.domain.services.IEmailSenderService;
import com.anderson.cache.domain.services.IPasswordEncoderService;
import com.anderson.cache.domain.services.IPasswordResetService;
import com.anderson.cache.domain.services.IRedisCodeService;
import com.anderson.cache.infra.exceptions.InvalidDataException;
import com.anderson.cache.infra.exceptions.NotFoundException;
import com.anderson.cache.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements IPasswordResetService {

    private final IRedisCodeService redisService;
    private final UserRepository repository;
    private final IEmailSenderService emailService;
    private final IPasswordEncoderService passwordEncoder;

    @Override
    public void sendResetCode(String email) {
        if (repository.findByEmail(email).isEmpty()) {
           throw new NotFoundException("User not found");
        }

        String code = String.format("%06d", new Random().nextInt(999999));
        redisService.save(email, code);

        emailService.sendEmail(email, "Recuperação de senha", "Seu código de recuperação é: " + code);
    }

    @Override
    public void resetPassword(String email, String code, String newPassword) {
        final String storedCode = redisService.get(email);

        if (isNull(storedCode) || !storedCode.equals(code)) {
            throw new InvalidDataException("Invalid or Expired Code");
        }

        final User user = repository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        redisService.delete(email);
    }
}
