package com.ecommerce.ordermanagementservice.controllers;

import com.ecommerce.ordermanagementservice.dtos.OrderItemDto;
import com.ecommerce.ordermanagementservice.dtos.OrderRequestDto;
import com.ecommerce.ordermanagementservice.dtos.OrderResponseDto;
import com.ecommerce.ordermanagementservice.exceptions.CustomerNotFoundException;
import com.ecommerce.ordermanagementservice.exceptions.OrderNotFoundException;
import com.ecommerce.ordermanagementservice.models.Order;
import com.ecommerce.ordermanagementservice.models.OrderItem;
import com.ecommerce.ordermanagementservice.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        Order createdOrder = orderService.createOrder(getOrder(orderRequestDto));
        return ResponseEntity.status(201).body(getOrderResponseDto(createdOrder));
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) throws OrderNotFoundException {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(getOrderResponseDto(order));
    }

    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByCustomerId(@PathVariable Long customerId) throws CustomerNotFoundException {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        for (Order order : orders) {
            orderResponseDtos.add(getOrderResponseDto(order));
        }
        return ResponseEntity.ok(orderResponseDtos);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) throws OrderNotFoundException {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    private OrderResponseDto getOrderResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setCustomerId(order.getCustomerId());
        orderResponseDto.setStatus(order.getStatus());
        orderResponseDto.setTotalAmount(order.getTotalAmount());
        orderResponseDto.setItems(getOrderItemDtos(order.getItems()));
        return orderResponseDto;
    }

    private List<OrderItemDto> getOrderItemDtos(List<OrderItem> orderItems){
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setProductId(orderItem.getProductId());
            orderItemDto.setQuantity(orderItem.getQuantity());
            orderItemDto.setPrice(orderItem.getPrice());
            orderItemDtos.add(orderItemDto);
        }
        return orderItemDtos;
    }

    private Order getOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setCustomerId(orderRequestDto.getCustomerId());
        order.setItems(getOrderItems(orderRequestDto.getItems()));
        return order;
    }

    private List<OrderItem> getOrderItems(List<OrderItemDto> orderItemDtos) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtos) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(orderItemDto.getProductId());
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setPrice(orderItemDto.getPrice());
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
