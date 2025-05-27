package com.ecommerce.usermanagementservice.exceptions;

public class SessionIsNotActive extends Exception {
    public SessionIsNotActive(String message) {
        super(message);
    }
}
