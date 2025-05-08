package com.ecommerce.paymentservice.dtos;

import com.ecommerce.paymentservice.models.PaymentMethod;
import com.ecommerce.paymentservice.models.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private Long id;
    private Long orderId;
    private double amount;
    private String paymentReferenceId;
    private PaymentStatus status;
    private PaymentMethod method;
}
