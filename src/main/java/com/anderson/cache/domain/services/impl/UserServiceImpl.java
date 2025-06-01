package com.anderson.cache.domain.services.impl;

import com.anderson.cache.domain.model.User;
import com.anderson.cache.domain.services.IPasswordEncoderService;
import com.anderson.cache.domain.services.IUserService;
import com.anderson.cache.infra.exceptions.DataConflictException;
import com.anderson.cache.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository repository;
    private final IPasswordEncoderService passwordEncoderService;

    @Override
    public User create(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new DataConflictException("User already registered");
        }

        final String hash = passwordEncoderService.encode(user.getPassword());
        user.setPassword(hash);

        return repository.save(user);
    }
}
