package com.ecommerce.paymentservice;

import com.ecommerce.paymentservice.models.Payment;
import com.ecommerce.paymentservice.models.PaymentMethod;
import com.ecommerce.paymentservice.models.PaymentStatus;
import com.ecommerce.paymentservice.repos.IPaymentRepository;
import com.ecommerce.paymentservice.services.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceApplicationTests {

    @Mock
    private IPaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void makePayment_successResponse() {
        // Arrange
        Payment payment = new Payment();
        payment.setOrderId(10L);
        payment.setAmount(100.0);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setMethod(PaymentMethod.UPI);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        // Act
        Payment response = paymentService.makePayment(payment);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(10L, response.getOrderId());
        Assertions.assertEquals(100.0, response.getAmount());
        Assertions.assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        Assertions.assertEquals(PaymentMethod.UPI, response.getMethod());

        // Verify
        verify(paymentRepository,times(1)).save(any(Payment.class));
    }

    @Test
    void getPaymentDetails_successResponse() throws Exception{
        // Arrange
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setOrderId(10L);
        payment.setAmount(100.0);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setMethod(PaymentMethod.UPI);

        when(paymentRepository.findById(1L)).thenReturn(java.util.Optional.of(payment));

        // Act
        Payment response = paymentService.getPaymentDetails(1L);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals(10L, response.getOrderId());
        Assertions.assertEquals(100.0, response.getAmount());
        Assertions.assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        Assertions.assertEquals(PaymentMethod.UPI, response.getMethod());

        // Verify
        verify(paymentRepository,times(1)).findById(1L);
    }

    @Test
    void getPaymentDetails_paymentNotFound() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Assertions.assertThrows(Exception.class, () -> {
            paymentService.getPaymentDetails(1L);
        });

        // Verify
        verify(paymentRepository,times(1)).findById(1L);
    }
}
