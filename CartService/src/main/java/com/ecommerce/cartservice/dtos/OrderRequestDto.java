package com.ecommerce.cartservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private Long customerId;
    private List<OrderItemDto> items;
}
