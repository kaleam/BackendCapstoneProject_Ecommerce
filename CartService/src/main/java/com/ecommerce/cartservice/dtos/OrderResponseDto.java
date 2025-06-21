package com.ecommerce.cartservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long customerId;
    private OrderStatus status;
    private double totalAmount;
    private List<OrderItemDto> items;
}
