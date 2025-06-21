package com.ecommerce.ordermanagementservice.services;

import com.ecommerce.ordermanagementservice.dtos.PaymentResponse;
import com.ecommerce.ordermanagementservice.exceptions.CustomerNotFoundException;
import com.ecommerce.ordermanagementservice.exceptions.NoItemsInCreateOrder;
import com.ecommerce.ordermanagementservice.exceptions.OrderNotFoundException;
import com.ecommerce.ordermanagementservice.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(Order order) throws NoItemsInCreateOrder;

    Order getOrderById(Long id) throws OrderNotFoundException;

    List<Order> getOrdersByCustomerId(Long customerId) throws CustomerNotFoundException;

    void cancelOrder(Long id) throws OrderNotFoundException;

    void updateOrderStatus(PaymentResponse response);
}
