package com.ecommerce.notificationservice.dtos;

import com.ecommerce.notificationservice.models.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private String userId;
    private String message;
    private String email;
    private String phoneNumber;
    private NotificationType type; // EMAIL or SMS
}
