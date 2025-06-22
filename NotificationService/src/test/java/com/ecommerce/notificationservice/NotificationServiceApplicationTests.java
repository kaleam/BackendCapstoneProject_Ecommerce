package com.ecommerce.notificationservice;

import com.ecommerce.notificationservice.models.NotificationEvent;
import com.ecommerce.notificationservice.models.NotificationType;
import com.ecommerce.notificationservice.services.EmailService;
import com.ecommerce.notificationservice.services.NotificationService;
import com.ecommerce.notificationservice.services.SmsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceApplicationTests {

    @Mock
    private EmailService emailService;

    @Mock
    private SmsService smsService;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void sendNotificationViaEmail_successResponse() {
        // Arrange
        NotificationEvent request = new NotificationEvent();
        request.setUserId("user123");
        request.setEmail("abc@xyz.com");
        request.setPhoneNumber("1234567890");
        request.setMessage("Your order has been placed successfully!");
        request.setType(NotificationType.EMAIL);

        // Act
        notificationService.sendNotification(request);

        // Assert

        // Verify
        verify(emailService, times(1)).sendEmail(request.getUserId(), request.getMessage());
    }

    @Test
    void sendNotificationViaSms_successResponse() {
        // Arrange
        NotificationEvent request = new NotificationEvent();
        request.setUserId("user123");
        request.setEmail("abc@xyz.com");
        request.setPhoneNumber("1234567890");
        request.setMessage("Your order has been placed successfully!");
        request.setType(NotificationType.SMS);

        // Act
        notificationService.sendNotification(request);

        // Assert

        // Verify
        verify(smsService, times(1)).sendSms(request.getUserId(), request.getMessage());
    }

}
