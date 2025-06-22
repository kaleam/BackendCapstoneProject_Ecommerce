package com.ecommerce.cartservice;

import com.ecommerce.cartservice.dtos.OrderItemDto;
import com.ecommerce.cartservice.dtos.OrderRequestDto;
import com.ecommerce.cartservice.dtos.OrderResponseDto;
import com.ecommerce.cartservice.dtos.OrderStatus;
import com.ecommerce.cartservice.models.*;
import com.ecommerce.cartservice.repos.ICartRepository;
import com.ecommerce.cartservice.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class CartServiceApplicationTests {

    @Mock
    private ICartRepository cartRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CartService cartService;

    @Test
    void addItem_successResponse() {
        // Arrange
        Long userId = 10L;
        CartItem item = new CartItem();
        item.setProductId(101L);
        item.setQuantity(1);
        item.setPrice(500.0);
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItems(List.of(item));

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class)))
                .thenReturn(cart);

        // Act
        Cart response = cartService.addItem(userId, item);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getItems().size());
        Assertions.assertEquals(101L, response.getItems().get(0).getProductId());
        Assertions.assertEquals(2, response.getItems().get(0).getQuantity());
        Assertions.assertEquals(500.0, response.getItems().get(0).getPrice());
        Assertions.assertEquals(userId, response.getUserId());

        // Verify
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void removeItem_successResponse() {
        // Arrange
        Long userId = 10L;
        CartItem item = new CartItem();
        item.setProductId(101L);
        item.setQuantity(1);
        item.setPrice(500.0);
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItems(new ArrayList<>(List.of(item)));

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class)))
                .thenReturn(cart);

        // Act
        Cart response = cartService.removeItem(userId, item.getProductId());

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getItems().isEmpty());
        Assertions.assertEquals(userId, response.getUserId());

        // Verify
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void clearCart_successResponse() {
        // Arrange
        Long userId = 10L;
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItems(new ArrayList<>());

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class)))
                .thenReturn(cart);

        // Act
        cartService.clearCart(userId);

        // Assert
        verify(cartRepository, times(1)).findByUserId(userId);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void getCart_successResponse() throws Exception{
        // Arrange
        Long userId = 10L;
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItems(new ArrayList<>());

        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.of(cart));

        // Act
        Cart response = cartService.getCart(userId);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(userId, response.getUserId());
        Assertions.assertTrue(response.getItems().isEmpty());

        // Verify
        verify(cartRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getCart_notFound() {
        // Arrange
        Long userId = 10L;
        when(cartRepository.findByUserId(userId))
                .thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> cartService.getCart(userId));

        // Verify
        verify(cartRepository, times(1)).findByUserId(userId);
    }

//    @Test
//    void checkout_successResponse() throws Exception {
//        // Arrange
//        Long userId = 10L;
//        CartItem item = new CartItem();
//        item.setProductId(101L);
//        item.setQuantity(1);
//        item.setPrice(500.0);
//        Cart cart = new Cart();
//        cart.setUserId(userId);
//        cart.setItems(new ArrayList<>(List.of(item)));
//
//        when(cartRepository.findByUserId(userId))
//                .thenReturn(Optional.of(cart));
//
//        OrderResponseDto orderResponseDto = new OrderResponseDto();
//        orderResponseDto.setId(100L);
//        orderResponseDto.setCustomerId(userId);
//        orderResponseDto.setStatus(OrderStatus.CREATED);
//        orderResponseDto.setTotalAmount(500.0);
//        ResponseEntity<OrderResponseDto> mockResponse = new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
//
//
//        // Prepare order request
//        OrderRequestDto orderRequestDto = new OrderRequestDto();
//        orderRequestDto.setCustomerId(userId);
//        OrderItemDto itemDto = new OrderItemDto();
//        itemDto.setProductId(item.getProductId());
//        itemDto.setQuantity(item.getQuantity());
//        itemDto.setPrice(item.getPrice());
//        orderRequestDto.setItems(new ArrayList<>(List.of(itemDto)));
//
//        // Prepare headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer dummyToken");
//        headers.set("Content-Type", "application/json");
//
//        // Prepare HTTP request
//        HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(orderRequestDto, headers);
//
//        when(restTemplate.postForEntity(eq("http://localhost:8080/api/order"),
//                any(HttpEntity.class),
//                eq(OrderResponseDto.class)
//                )).thenReturn(mockResponse);
//
//        // Act
//        Long orderId = cartService.checkout(userId, "dummyToken");
//
//        // Assert
//        Assertions.assertNotNull(orderId);
//        Assertions.assertEquals(100L, orderId);
//        Assertions.assertEquals(100L, orderResponseDto.getId());
//        Assertions.assertEquals(OrderStatus.CREATED, orderResponseDto.getStatus());
//        Assertions.assertEquals(500.0, orderResponseDto.getTotalAmount());
//
//        // Verify
//        verify(cartRepository, times(1)).findByUserId(userId);
//        verify(restTemplate, times(1)).postForEntity(anyString(), any(), eq(OrderResponseDto.class));
//    }

}
