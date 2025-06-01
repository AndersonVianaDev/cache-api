package com.anderson.cache.domain.services;

public interface IEmailSenderService {
    void sendEmail(String to, String subject, String body);
}
