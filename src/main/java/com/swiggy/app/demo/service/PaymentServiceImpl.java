package com.swiggy.app.demo.service;

import com.swiggy.app.demo.entity.Payment;
import com.swiggy.app.demo.entity.PaymentMethod;
import com.swiggy.app.demo.entity.PaymentStatus;
import com.swiggy.app.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(String orderId, double amount, PaymentMethod paymentMethod) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(PaymentStatus.PENDING);

        switch (paymentMethod) {
            case COD:
                processCOD(payment);
                break;
            case UPI:
                processUPI(payment);
                break;
            case CREDIT_CARD:
            case DEBIT_CARD:
                processCardPayment(payment, paymentMethod);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported payment method");
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment refundPayment(Payment payment) {
        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            payment.setStatus(PaymentStatus.PENDING);
            System.out.println("Refund initiated for payment ID: " + payment.getId());
            payment.setStatus(PaymentStatus.COMPLETED);
        } else {
            throw new IllegalStateException("Cannot refund payment with status: " + payment.getStatus());
        }

        return paymentRepository.save(payment); // Save the updated payment
    }

    private void processCOD(Payment payment) {
        payment.setStatus(PaymentStatus.COMPLETED);
        System.out.println("Payment completed via COD.");
    }

    private void processUPI(Payment payment) {
        if (simulateUPITransaction()) {
            payment.setStatus(PaymentStatus.COMPLETED);
            System.out.println("Payment completed via UPI.");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println("UPI payment failed.");
        }
    }

    private void processCardPayment(Payment payment, PaymentMethod method) {
        if (simulateCardTransaction()) {
            payment.setStatus(PaymentStatus.COMPLETED);
            System.out.println("Payment completed via " + method + ".");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println(method + " payment failed.");
        }
    }

    private boolean simulateUPITransaction() {
        return true;
    }

    private boolean simulateCardTransaction() {
        return true;
    }
}
