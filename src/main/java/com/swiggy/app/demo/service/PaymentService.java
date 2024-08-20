package com.swiggy.app.demo.service;

import com.swiggy.app.demo.entity.Payment;
import com.swiggy.app.demo.entity.PaymentMethod;
import org.springframework.stereotype.Service;

/**
 * @author
  **/
@Service
public interface PaymentService {
    Payment processPayment(String orderId, double amount, PaymentMethod paymentMethod);

    Payment refundPayment(Payment payment);
}
