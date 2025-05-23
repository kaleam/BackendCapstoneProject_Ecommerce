package com.ecommerce.notificationservice.services;

import com.ecommerce.notificationservice.models.NotificationEvent;

public interface INotificationService {
    void sendNotification(NotificationEvent request);
}
