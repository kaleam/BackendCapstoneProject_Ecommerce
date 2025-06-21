package com.ecommerce.cartservice.dtos;

public enum OrderStatus {
    CREATED,
    CONFIRMED,
    PENDING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED,
    REFUNDED,
    FAILED
}
