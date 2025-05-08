package com.ecommerce.paymentservice.controllers;

import com.ecommerce.paymentservice.dtos.PaymentRequestDto;
import com.ecommerce.paymentservice.dtos.PaymentResponseDto;
import com.ecommerce.paymentservice.exceptions.PaymentIdNotFoundException;
import com.ecommerce.paymentservice.models.Payment;
import com.ecommerce.paymentservice.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController {
    @Autowired
    IPaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> makePayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentService.makePayment(getPayment(paymentRequestDto));
        return ResponseEntity.ok(getPaymentResponseDto(payment));
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentDetails(@PathVariable Long id) throws PaymentIdNotFoundException {
        Payment payment = paymentService.getPaymentDetails(id);
        return ResponseEntity.ok(getPaymentResponseDto(payment));
    }

    private Payment getPayment(PaymentRequestDto paymentRequestDto) {
        Payment payment = new Payment();
        payment.setOrderId(paymentRequestDto.getOrderId());
        payment.setAmount(paymentRequestDto.getAmount());
        return payment;
    }

    private PaymentResponseDto getPaymentResponseDto(Payment payment) {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setId(payment.getId());
        paymentResponseDto.setOrderId(payment.getOrderId());
        paymentResponseDto.setAmount(payment.getAmount());
        paymentResponseDto.setStatus(payment.getStatus());
        paymentResponseDto.setMethod(payment.getMethod());
        paymentResponseDto.setPaymentReferenceId(payment.getPaymentReferenceId());
        return paymentResponseDto;
    }
}
