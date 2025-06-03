package com.ecommerce.paymentservice.services;

import com.ecommerce.paymentservice.dtos.PaymentRequest;
import com.ecommerce.paymentservice.exceptions.PaymentIdNotFoundException;
import com.ecommerce.paymentservice.models.Payment;

public interface IPaymentService {
    Payment makePayment(Payment payment);

    Payment getPaymentDetails(Long id) throws PaymentIdNotFoundException;

    void process(PaymentRequest request);
}
