package com.ecommerce.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private Long orderId;
    private Long userId;
    private double amount;
}
