package com.ecommerce.usermanagementservice.dtos;

import com.ecommerce.usermanagementservice.models.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEvent {
    private String userId;
    private String message;
    private String email;
    private String phoneNumber;
    private NotificationType type; // EMAIL or SMS
}
