package com.ecommerce.notificationservice.services;

import com.ecommerce.notificationservice.models.NotificationEvent;
import com.ecommerce.notificationservice.models.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;

    @Override
    public void sendNotification(NotificationEvent request) {
        switch (request.getType()) {
            case NotificationType.EMAIL:
                emailService.sendEmail(request.getUserId(), request.getMessage());
                break;
            case NotificationType.SMS:
                smsService.sendSms(request.getUserId(), request.getMessage());
                break;
            default:
                throw new IllegalArgumentException("Unsupported notification type");
        }
    }
}
