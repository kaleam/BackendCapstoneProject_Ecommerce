package com.ecommerce.cartservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
}
