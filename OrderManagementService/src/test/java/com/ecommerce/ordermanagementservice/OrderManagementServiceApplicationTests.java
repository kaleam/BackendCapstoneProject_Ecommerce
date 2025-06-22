package com.ecommerce.ordermanagementservice;

import com.ecommerce.ordermanagementservice.models.Order;
import com.ecommerce.ordermanagementservice.models.OrderItem;
import com.ecommerce.ordermanagementservice.models.OrderStatus;
import com.ecommerce.ordermanagementservice.producers.PaymentEventProducer;
import com.ecommerce.ordermanagementservice.repos.IOrderRepository;
import com.ecommerce.ordermanagementservice.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderManagementServiceApplicationTests {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private PaymentEventProducer paymentEventProducer;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_shouldReturn201AndOrderResponse() {
        // Arrange
        Order orderRequest = new Order();
        orderRequest.setCustomerId(1L);
        OrderItem item = new OrderItem();
        item.setProductId(101L);
        item.setQuantity(2);
        item.setPrice(500.0);
        orderRequest.setItems(List.of(item));

        Order createdOrder = new Order();
        createdOrder.setId(100L);
        createdOrder.setCustomerId(1L);
        createdOrder.setStatus(OrderStatus.CREATED);
        createdOrder.setTotalAmount(1000.0);
        OrderItem itemEntity = new OrderItem();
        itemEntity.setProductId(101L);
        itemEntity.setQuantity(2);
        itemEntity.setPrice(500.0);
        createdOrder.setItems(List.of(itemEntity));

        when(orderRepository.save(any(Order.class)))
                .thenReturn(createdOrder)
                .thenReturn(createdOrder)
                .thenReturn(createdOrder);

        // Act
        Order response = orderService.createOrder(orderRequest);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(100L, response.getId());
        Assertions.assertEquals(OrderStatus.CREATED, response.getStatus());
        Assertions.assertEquals(1000.0, response.getTotalAmount());
        Assertions.assertEquals(1, response.getItems().size());

        // Verify
        verify(orderRepository, times(3)).save(any(Order.class));
        verify(paymentEventProducer, times(1)).sendPaymentRequest(any());
    }

    @Test
    void getOrderById_shouldReturnOrder() throws Exception{
        // Arrange
        Order order = new Order();
        order.setId(100L);
        order.setCustomerId(1L);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(999.0);
        OrderItem item = new OrderItem();
        item.setProductId(101L);
        item.setQuantity(2);
        item.setPrice(500.0);
        order.setItems(List.of(item));

        when(orderRepository.findById(100L)).thenReturn(java.util.Optional.of(order));

        // Act
        Order response = orderService.getOrderById(100L);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(100L, response.getId());
        Assertions.assertEquals(OrderStatus.CONFIRMED, response.getStatus());
        Assertions.assertEquals(999.0, response.getTotalAmount());
        Assertions.assertEquals(1, response.getItems().size());

        // Verify
        verify(orderRepository, times(1)).findById(100L);
    }

    @Test
    void getOrderById_shouldThrowException(){
        // Arrange
        when(orderRepository.findById(100L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            orderService.getOrderById(100L);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Order not found", exception.getMessage());

        // Verify
        verify(orderRepository, times(1)).findById(100L);
    }

    @Test
    void getOrdersByCustomerId_shouldReturnList() throws Exception{
        // Arrange
        Order order1 = new Order();
        order1.setId(100L);
        order1.setCustomerId(1L);
        order1.setStatus(OrderStatus.CONFIRMED);
        order1.setTotalAmount(999.0);
        OrderItem item1 = new OrderItem();
        item1.setProductId(101L);
        item1.setQuantity(2);
        item1.setPrice(500.0);
        order1.setItems(List.of(item1));

        Order order2 = new Order();
        order2.setId(101L);
        order2.setCustomerId(1L);
        order2.setStatus(OrderStatus.PENDING);
        order2.setTotalAmount(500.0);
        OrderItem item2 = new OrderItem();
        item2.setProductId(102L);
        item2.setQuantity(1);
        item2.setPrice(500.0);
        order2.setItems(List.of(item2));

        when(orderRepository.findByCustomerId(1L)).thenReturn(List.of(order1, order2));

        // Act
        List<Order> response = orderService.getOrdersByCustomerId(1L);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals(100L, response.get(0).getId());
        Assertions.assertEquals(OrderStatus.CONFIRMED, response.get(0).getStatus());
        Assertions.assertEquals(999.0, response.get(0).getTotalAmount());
        Assertions.assertEquals(1, response.get(0).getItems().size());
        Assertions.assertEquals(101L, response.get(1).getId());
        Assertions.assertEquals(OrderStatus.PENDING, response.get(1).getStatus());
        Assertions.assertEquals(500.0, response.get(1).getTotalAmount());
        Assertions.assertEquals(1, response.get(1).getItems().size());

        // Verify
        verify(orderRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void cancelOrder_shouldReturn204() throws Exception{
        // Arrange
        Long orderId = 100L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.CONFIRMED);

//        Order cancelledOrder = new Order();
//        cancelledOrder.setId(orderId);
//        cancelledOrder.setStatus(OrderStatus.CANCELLED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        //when(orderRepository.save(any(Order.class))).thenReturn(cancelledOrder);

        // Act
        orderService.cancelOrder(orderId);

        // Assert

        // Verify
        verify(orderRepository, times(1)).findById(orderId);
        //verify(orderRepository, times(1)).save(cancelledOrder);
    }
}
