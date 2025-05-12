package com.ecommerce.cartservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
public class CartDto {
    Long userId;
    List<CartItemDto> items;
}
