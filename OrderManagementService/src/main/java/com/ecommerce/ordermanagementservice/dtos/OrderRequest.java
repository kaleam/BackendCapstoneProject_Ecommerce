package com.ecommerce.ordermanagementservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private List<Long> productId;
    private double amount;
}
