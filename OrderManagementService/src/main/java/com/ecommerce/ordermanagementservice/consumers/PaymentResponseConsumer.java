package com.ecommerce.ordermanagementservice.consumers;

import com.ecommerce.ordermanagementservice.dtos.PaymentResponse;
import com.ecommerce.ordermanagementservice.services.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseConsumer {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-responses", groupId = "order-service")
    public void consume(String message) {
        try {
            PaymentResponse response = objectMapper.readValue(message, PaymentResponse.class);
            orderService.updateOrderStatus(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

