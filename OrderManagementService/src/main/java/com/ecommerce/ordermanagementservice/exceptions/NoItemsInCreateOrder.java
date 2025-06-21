package com.ecommerce.ordermanagementservice.exceptions;

public class NoItemsInCreateOrder extends RuntimeException {
    public NoItemsInCreateOrder(String message) {
        super(message);
    }
}
