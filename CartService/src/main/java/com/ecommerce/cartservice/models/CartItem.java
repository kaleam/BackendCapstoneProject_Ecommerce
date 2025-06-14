package com.ecommerce.cartservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem extends BaseModel{
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
}

