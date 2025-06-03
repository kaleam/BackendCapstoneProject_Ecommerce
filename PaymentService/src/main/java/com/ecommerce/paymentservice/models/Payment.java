package com.ecommerce.paymentservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "payments")
@Getter
@Setter
public class Payment extends BaseModel {
    private Long orderId;
    private double amount;
    private String paymentReferenceId;
    private PaymentStatus status;
    private PaymentMethod method;
}
