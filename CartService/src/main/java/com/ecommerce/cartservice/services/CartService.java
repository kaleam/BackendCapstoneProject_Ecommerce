package com.ecommerce.cartservice.services;

import com.ecommerce.cartservice.exceptions.CartNotFoundException;
import com.ecommerce.cartservice.models.Cart;
import com.ecommerce.cartservice.models.CartItem;
import com.ecommerce.cartservice.repos.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartService implements ICartService{
    @Autowired
    private ICartRepository cartRepository;

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                Cart cart = new Cart();
                cart.setUserId(userId);
                return cartRepository.save(cart);
            });
    }

    @Override
    @CachePut(value = "cart", key = "#userId")
    public Cart addItem(Long userId, CartItem item) {
        Cart cart = getOrCreateCart(userId);
        boolean itemUpdated = false;

        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getProductId().equals(item.getProductId())) {
                // Increment quantity
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                itemUpdated = true;
                break;
            }
        }

        // Add new item if it does not exist
        if (!itemUpdated) {
            cart.getItems().add(item);
        }
        return cartRepository.save(cart);
    }

    @Override
    @CachePut(value = "cart", key = "#userId")
    public Cart removeItem(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        return cartRepository.save(cart);
    }

    @Override
    @CacheEvict(value = "cart", key = "#userId")
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    @Cacheable(value = "cart", key = "#userId")
    public Cart getCart(Long userId) throws CartNotFoundException {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException("Cart not found for user ID: " + userId);
        }
        return cartOptional.get();
    }
}
