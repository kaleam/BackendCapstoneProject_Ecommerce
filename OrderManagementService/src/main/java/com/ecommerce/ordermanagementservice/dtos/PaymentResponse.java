package com.ecommerce.ordermanagementservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    private Long orderId;
    private String status; // SUCCESS, FAILED
}
