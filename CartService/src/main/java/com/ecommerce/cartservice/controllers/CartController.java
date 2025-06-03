package com.ecommerce.cartservice.controllers;

import com.ecommerce.cartservice.dtos.CartDto;
import com.ecommerce.cartservice.dtos.CartItemDto;
import com.ecommerce.cartservice.exceptions.CartNotFoundException;
import com.ecommerce.cartservice.models.Cart;
import com.ecommerce.cartservice.models.CartItem;
import com.ecommerce.cartservice.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private ICartService cartService;

    @GetMapping("user/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) throws CartNotFoundException {
        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(getCartDto(cart));
    }

    @PostMapping("user/{userId}/items")
    public ResponseEntity<CartDto> addItem(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto){
        CartItem cartItem = getCartItem(cartItemDto);
        Cart cart = cartService.addItem(userId, cartItem);
        return ResponseEntity.ok(getCartDto(cart));
    }

    @DeleteMapping("user/{userId}/items/{productId}")
    public ResponseEntity<CartDto> removeItem(@PathVariable Long userId, @PathVariable Long productId){
        return ResponseEntity.ok(getCartDto(cartService.removeItem(userId, productId)));
    }

    @DeleteMapping("user/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId){
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    private CartDto getCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUserId());
        cartDto.setItems(getCartItemDto(cart.getItems()));
        return cartDto;
    }

    private List<CartItemDto> getCartItemDto(List<CartItem> cartItems) {
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setProductId(cartItem.getProductId());
            cartItemDto.setQuantity(cartItem.getQuantity());
            cartItemDto.setPrice(cartItem.getPrice());
            cartItemDto.setProductName(cartItem.getProductName());
            cartItemDtos.add(cartItemDto);
        }
        return cartItemDtos;
    }

    private CartItem getCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setPrice(cartItemDto.getPrice());
        cartItem.setProductName(cartItemDto.getProductName());
        return cartItem;
    }

}
