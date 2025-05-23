package com.ecommerce.notificationservice.consumers;

import com.ecommerce.notificationservice.dtos.NotificationRequest;
import com.ecommerce.notificationservice.models.NotificationEvent;
import com.ecommerce.notificationservice.services.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NotificationConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void listen(String message) throws Exception {
        NotificationRequest request = objectMapper.readValue(message, NotificationRequest.class);
        notificationService.sendNotification(convertToNotificationEvent(request));
    }

    private NotificationEvent convertToNotificationEvent(NotificationRequest request) {
        NotificationEvent event = new NotificationEvent();
        event.setUserId(request.getUserId());
        event.setEmail(request.getEmail());
        event.setPhoneNumber(request.getPhoneNumber());
        event.setMessage(request.getMessage());
        event.setType(request.getType());
        return event;
    }
}
