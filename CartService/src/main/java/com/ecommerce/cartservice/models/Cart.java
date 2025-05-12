package com.ecommerce.cartservice.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "cart")
@Getter
@Setter
public class Cart extends BaseModel{
    private Long userId;
    private List<CartItem> items = new ArrayList<>();
}
