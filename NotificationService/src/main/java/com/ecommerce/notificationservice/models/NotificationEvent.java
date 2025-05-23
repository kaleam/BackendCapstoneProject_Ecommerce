package com.ecommerce.notificationservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationEvent {
    private String userId;
    private String email;
    private String phoneNumber;
    private String message;
    private NotificationType type; // EMAIL or SMS
}
