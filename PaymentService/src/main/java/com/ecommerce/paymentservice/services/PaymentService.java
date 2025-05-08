package com.ecommerce.paymentservice.services;

import com.ecommerce.paymentservice.exceptions.PaymentIdNotFoundException;
import com.ecommerce.paymentservice.models.Payment;
import com.ecommerce.paymentservice.models.PaymentMethod;
import com.ecommerce.paymentservice.models.PaymentStatus;
import com.ecommerce.paymentservice.repos.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService implements IPaymentService{
    @Autowired
    private IPaymentRepository paymentRepository;

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
}
