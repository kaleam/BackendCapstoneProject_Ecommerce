package com.ecommerce.cartservice.exceptions;

public class CreateOrderRequestFailed extends RuntimeException {
    public CreateOrderRequestFailed(String message) {
        super(message);
    }
}
