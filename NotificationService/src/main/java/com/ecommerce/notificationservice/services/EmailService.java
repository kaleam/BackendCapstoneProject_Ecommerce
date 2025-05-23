package com.ecommerce.notificationservice.services;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String userId, String message) {
        System.out.printf("Sending EMAIL to %s: %s%n", userId, message);
    }
}
