package com.ecommerce.ordermanagementservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class OrderManagementServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder_shouldReturn201AndOrderResponse() {
        OrderRequestDto request = new OrderRequestDto(1L, List.of(10L, 20L), 999.0);
        OrderResponseDto response = new OrderResponseDto(100L, "PLACED", 999.0, LocalDateTime.now());

        Mockito.when(orderService.createOrder(Mockito.any())).thenReturn(response);

        ResponseEntity<OrderResponseDto> result = restTemplate.postForEntity(
                "/api/orders", request, OrderResponseDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(100L, result.getBody().getOrderId());
    }

    @Test
    void getOrderById_shouldReturnOrder() {
        OrderResponseDto response = new OrderResponseDto(100L, "PLACED", 999.0, LocalDateTime.now());

        Mockito.when(orderService.getOrderById(100L)).thenReturn(response);

        ResponseEntity<OrderResponseDto> result = restTemplate.getForEntity(
                "/api/orders/100", OrderResponseDto.class);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals("PLACED", result.getBody().getStatus());
    }

    @Test
    void getOrdersByCustomerId_shouldReturnList() {
        List<OrderResponseDto> orders = List.of(
                new OrderResponseDto(100L, "PLACED", 999.0, LocalDateTime.now()),
                new OrderResponseDto(101L, "DELIVERED", 1099.0, LocalDateTime.now())
        );

        Mockito.when(orderService.getOrdersByCustomerId(1L)).thenReturn(orders);

        ResponseEntity<OrderResponseDto[]> result = restTemplate.getForEntity(
                "/api/orders/customer/1", OrderResponseDto[].class);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(2, result.getBody().length);
    }

    @Test
    void cancelOrder_shouldReturn204() {
        Mockito.doNothing().when(orderService).cancelOrder(100L);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/orders/100/cancel",
                HttpMethod.PUT,
                requestEntity,
                Void.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
