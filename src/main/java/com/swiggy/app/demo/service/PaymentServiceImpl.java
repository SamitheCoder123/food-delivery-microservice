package com.swiggy.app.demo.service.impl;

import com.swiggy.app.demo.entity.Payment;
import com.swiggy.app.demo.entity.PaymentMethod;
import com.swiggy.app.demo.entity.PaymentStatus;
import com.swiggy.app.demo.repository.PaymentRepository;
import com.swiggy.app.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(String orderId, double amount, PaymentMethod paymentMethod) {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING)
                .build();

        switch (paymentMethod) {
            case COD:
                processCOD(payment);
                break;
            case UPI:
                processUPI(payment);
                break;
            case CREDIT_CARD:
            case DEBIT_CARD:
                processCardPayment(payment);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported payment method: " + paymentMethod);
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment refundPayment(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with ID: " + paymentId);
        }

        Payment payment = paymentOpt.get();
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot refund payment with status: " + payment.getStatus());
        }

        payment.setStatus(PaymentStatus.PENDING);
        // Simulate refund processing
        boolean refundSuccess = simulateRefundTransaction();
        if (refundSuccess) {
            payment.setStatus(PaymentStatus.COMPLETED);
            System.out.println("Refund completed for payment ID: " + paymentId);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println("Refund failed for payment ID: " + paymentId);
        }

        return paymentRepository.save(payment);
    }

    private void processCOD(Payment payment) {
        payment.setStatus(PaymentStatus.COMPLETED);
        System.out.println("Payment completed via COD.");
    }

    private void processUPI(Payment payment) {
        boolean transactionSuccess = simulateUPITransaction();
        if (transactionSuccess) {
            payment.setStatus(PaymentStatus.COMPLETED);
            System.out.println("Payment completed via UPI.");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println("UPI payment failed.");
        }
    }

    private void processCardPayment(Payment payment) {
        boolean transactionSuccess = simulateCardTransaction();
        if (transactionSuccess) {
            payment.setStatus(PaymentStatus.COMPLETED);
            System.out.println("Payment completed via " + payment.getPaymentMethod() + ".");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println(payment.getPaymentMethod() + " payment failed.");
        }
    }

    private boolean simulateUPITransaction() {
        // Simulate UPI transaction logic here
        return true; // For testing purposes, always successful
    }

    private boolean simulateCardTransaction() {
        // Simulate card transaction logic here
        return true; // For testing purposes, always successful
    }

    private boolean simulateRefundTransaction() {
        // Simulate refund transaction logic here
        return true; // For testing purposes, always successful
    }
}
