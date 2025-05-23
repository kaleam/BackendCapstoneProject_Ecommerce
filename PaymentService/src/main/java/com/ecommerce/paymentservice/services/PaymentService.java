package com.ecommerce.paymentservice.services;

import com.ecommerce.paymentservice.dtos.PaymentRequest;
import com.ecommerce.paymentservice.dtos.PaymentResponse;
import com.ecommerce.paymentservice.exceptions.PaymentIdNotFoundException;
import com.ecommerce.paymentservice.models.Payment;
import com.ecommerce.paymentservice.models.PaymentMethod;
import com.ecommerce.paymentservice.models.PaymentStatus;
import com.ecommerce.paymentservice.producers.PaymentResponseProducer;
import com.ecommerce.paymentservice.repos.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements IPaymentService{
    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private PaymentResponseProducer responseProducer;

    @Override
    public Payment makePayment(Payment payment) {
        payment.setPaymentReferenceId(generateTransactionId());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setMethod(PaymentMethod.UPI);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentDetails(Long id) throws PaymentIdNotFoundException {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if(paymentOptional.isEmpty()){
            throw new PaymentIdNotFoundException("Payment ID not found");
        }
        return paymentOptional.get();
    }

    private String generateTransactionId() {
        // Generate a unique transaction ID
        return String.valueOf(System.currentTimeMillis());
    }

    public void process(PaymentRequest request) {
        // Simulate payment logic
        boolean success = Math.random() > 0.2; // 80% chance of success

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        //payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);

        paymentRepository.save(payment);

        PaymentResponse response = new PaymentResponse();
        response.setOrderId(request.getOrderId());
        response.setStatus(payment.getStatus().toString());

        responseProducer.send(response);
    }
}
