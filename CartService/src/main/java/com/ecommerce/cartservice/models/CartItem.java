package com.ecommerce.cartservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
}

