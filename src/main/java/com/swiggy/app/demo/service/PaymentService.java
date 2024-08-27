package com.swiggy.app.demo.service;

import com.swiggy.app.demo.entity.Payment;
import com.swiggy.app.demo.entity.PaymentMethod;

import java.util.Optional;

public interface PaymentService {
    Payment processPayment(String orderId, double amount, PaymentMethod paymentMethod,String upiId,String linkedPhoneNumber,String password);
    Optional<Payment> getPaymentById(Long id);
    Payment refundPayment(Long paymentId);
}
