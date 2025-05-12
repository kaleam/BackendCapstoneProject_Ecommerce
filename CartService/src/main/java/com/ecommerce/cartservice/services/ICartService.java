package com.ecommerce.cartservice.services;

import com.ecommerce.cartservice.exceptions.CartNotFoundException;
import com.ecommerce.cartservice.models.Cart;
import com.ecommerce.cartservice.models.CartItem;

public interface ICartService {
    Cart addItem(Long userId, CartItem item);
    Cart removeItem(Long userId, Long productId);
    void clearCart(Long userId);
    Cart getCart(Long userId) throws CartNotFoundException;
}
