package com.ecommerce.ordermanagementservice.services;

import com.ecommerce.ordermanagementservice.exceptions.CustomerNotFoundException;
import com.ecommerce.ordermanagementservice.exceptions.OrderNotFoundException;
import com.ecommerce.ordermanagementservice.models.Order;
import com.ecommerce.ordermanagementservice.models.OrderItem;
import com.ecommerce.ordermanagementservice.models.OrderStatus;
import com.ecommerce.ordermanagementservice.repos.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        for(OrderItem item : order.getItems()){
            item.setOrder(order);
        }
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);

        // check inventory

        order.setStatus(OrderStatus.PENDING);
        // calculate total amount
        double totalAmount = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        // make payment

        order.setStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order not found");
        }
        return orderOptional.get();
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) throws CustomerNotFoundException {
        List<Order> orderList = orderRepository.findByCustomerId(customerId);
        if(orderList.isEmpty()){
            throw new CustomerNotFoundException("Customer not found");
        }
        return orderList;
    }

    @Override
    public void cancelOrder(Long id) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()){
            throw new OrderNotFoundException("Order not found");
        }
        Order order = orderOptional.get();
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
