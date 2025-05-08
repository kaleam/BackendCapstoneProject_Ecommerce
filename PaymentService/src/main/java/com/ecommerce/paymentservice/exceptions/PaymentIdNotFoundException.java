package com.ecommerce.paymentservice.exceptions;

public class PaymentIdNotFoundException extends Exception {
    public PaymentIdNotFoundException(String message) {
        super(message);
    }
}
