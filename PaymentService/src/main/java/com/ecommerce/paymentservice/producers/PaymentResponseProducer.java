package com.ecommerce.paymentservice.producers;

import com.ecommerce.paymentservice.dtos.PaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String TOPIC = "payment-responses";

    public void send(PaymentResponse response) {
        try {
            String message = objectMapper.writeValueAsString(response);
            kafkaTemplate.send(TOPIC, response.getOrderId().toString(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

