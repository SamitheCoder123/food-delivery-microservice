package com.swiggy.app.demo.service;

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
    public Payment processPayment(String orderId, double amount, PaymentMethod paymentMethod,String upiId,String linkedPhoneNumber,String password) {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING)
                .upiId(upiId)
                .linkedPhoneNumber(linkedPhoneNumber)
                .password(password)
                .build();

        switch (paymentMethod) {
            case COD:
                processCOD(payment);
                break;
            case UPI_GOOGLE_PAY:
            case UPI_PHONEPE:
            case UPI_PAYTM:
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
        boolean isValid;
        switch (payment.getPaymentMethod()) {
            case UPI_GOOGLE_PAY:
                isValid = validateGooglePay(payment.getUpiId(), payment.getLinkedPhoneNumber(), payment.getPassword());
                break;
            case UPI_PHONEPE:
                isValid = validatePhonePe(payment.getUpiId(), payment.getLinkedPhoneNumber(), payment.getPassword());
                break;
            case UPI_PAYTM:
                isValid = validatePaytm(payment.getUpiId(), payment.getLinkedPhoneNumber(), payment.getPassword());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported UPI method: " + payment.getPaymentMethod());
        }

        if (!isValid) {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println("UPI validation failed for " + payment.getPaymentMethod());
            return;
        }

        boolean transactionSuccess = simulateUPITransaction(payment.getPaymentMethod().getMethod());
        if (transactionSuccess) {
            payment.setStatus(PaymentStatus.COMPLETED);
            System.out.println("Payment completed via " + payment.getPaymentMethod() + ".");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println(payment.getPaymentMethod() + " payment failed.");
        }
    }

    private boolean validateGooglePay(String upiId, String linkedPhoneNumber, String password) {
        // Google Pay specific validation logic
        return validateUPICommonFields(upiId, linkedPhoneNumber, password);
    }

    private boolean validatePhonePe(String upiId, String linkedPhoneNumber, String password) {
        // PhonePe specific validation logic
        return validateUPICommonFields(upiId, linkedPhoneNumber, password);
    }

    private boolean validatePaytm(String upiId, String linkedPhoneNumber, String password) {
        // Paytm specific validation logic
        return validateUPICommonFields(upiId, linkedPhoneNumber, password);
    }

    private boolean validateUPICommonFields(String upiId, String linkedPhoneNumber, String password) {
        // Common UPI validation logic
        if (upiId == null || upiId.isEmpty()) {
            System.out.println("Invalid UPI ID.");
            return false;
        }
        if (linkedPhoneNumber == null || linkedPhoneNumber.isEmpty()) {
            System.out.println("Invalid linked phone number.");
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Invalid password.");
            return false;
        }
        // Further validation logic, such as checking formats or database records, can be added here
        return true;
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

    private boolean simulateUPITransaction(String upiProvider) {
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
