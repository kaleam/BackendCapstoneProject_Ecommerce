package com.ecommerce.notificationservice.services;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void sendSms(String userId, String message) {
        System.out.printf("Sending SMS to %s: %s%n", userId, message);
    }
}
