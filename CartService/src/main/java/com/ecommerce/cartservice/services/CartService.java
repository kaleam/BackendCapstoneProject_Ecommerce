package com.ecommerce.cartservice.services;

import com.ecommerce.cartservice.dtos.OrderItemDto;
import com.ecommerce.cartservice.dtos.OrderRequestDto;
import com.ecommerce.cartservice.dtos.OrderResponseDto;
import com.ecommerce.cartservice.exceptions.CartHasNoItems;
import com.ecommerce.cartservice.exceptions.CartNotFoundException;
import com.ecommerce.cartservice.exceptions.CreateOrderRequestFailed;
import com.ecommerce.cartservice.models.Cart;
import com.ecommerce.cartservice.models.CartItem;
import com.ecommerce.cartservice.repos.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService{
    @Autowired
    private ICartRepository cartRepository;

    private final RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    public CartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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

    @Override
    public Long checkout(Long userId, String token) throws CartNotFoundException, CreateOrderRequestFailed, CartHasNoItems {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException("Cart not found for user ID: " + userId);
        }
        Cart cart = cartOptional.get();
        if (cart.getItems().isEmpty()) {
            throw new CartHasNoItems("Cart is empty, cannot create order.");
        }
        // Prepare order request
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setCustomerId(userId);
        orderRequestDto.setItems(getOrderItemDto(cart.getItems()));

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");

        // Prepare HTTP request
        HttpEntity <OrderRequestDto> requestEntity = new HttpEntity<>(orderRequestDto, headers);

        // Send request to order service
        ResponseEntity<OrderResponseDto> response = restTemplate.postForEntity(orderServiceUrl, requestEntity, OrderResponseDto.class);

        if(response.getStatusCode().is2xxSuccessful()) {
            OrderResponseDto orderResponse = response.getBody();
            if (orderResponse.getId() != null) {
                // Clear cart after successful order creation
                clearCart(userId);
                return orderResponse.getId();
            } else {
                throw new CreateOrderRequestFailed("Failed to create order, no order ID returned.");
            }
        } else {
            throw new CreateOrderRequestFailed("Failed to create order, status code: " + response.getStatusCode());
        }
    }

    private List<OrderItemDto> getOrderItemDto(List<CartItem> cartItems) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for( CartItem cartItem : cartItems) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setProductId(cartItem.getProductId());
            orderItemDto.setQuantity(cartItem.getQuantity());
            orderItemDto.setPrice(cartItem.getPrice());
            orderItemDtos.add(orderItemDto);
        }

        return orderItemDtos;
    }
}
