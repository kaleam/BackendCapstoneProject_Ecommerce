package com.ecommerce.paymentservice.consumers;

import com.ecommerce.paymentservice.dtos.PaymentRequest;
import com.ecommerce.paymentservice.services.IPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// consumer/PaymentRequestConsumer.java
@Component
public class PaymentRequestConsumer {

    @Autowired
    private IPaymentService paymentProcessorService;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String TOPIC = "payment-requests";

    @KafkaListener(topics = TOPIC, groupId = "payment-service")
    public void listen(String message) {
        try {
            PaymentRequest request = objectMapper.readValue(message, PaymentRequest.class);
            paymentProcessorService.process(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

