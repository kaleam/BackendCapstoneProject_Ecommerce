package com.ecommerce.cartservice.exceptions;

public class CartHasNoItems extends RuntimeException {
    public CartHasNoItems(String message) {
        super(message);
    }
}
