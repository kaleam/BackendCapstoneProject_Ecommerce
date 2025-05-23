package com.ecommerce.ordermanagementservice.producers;

import com.ecommerce.ordermanagementservice.dtos.PaymentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String TOPIC = "payment-requests";

    public void sendPaymentRequest(PaymentRequest request) {
        try {
            kafkaTemplate.send(TOPIC, request.getOrderId().toString(), objectMapper.writeValueAsString(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

