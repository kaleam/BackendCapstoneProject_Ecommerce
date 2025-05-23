package com.ecommerce.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    private Long orderId;
    private String status; // SUCCESS, FAILED
}
