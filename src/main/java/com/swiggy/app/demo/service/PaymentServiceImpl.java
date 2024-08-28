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
    public Payment processPayment(String orderId, double amount, PaymentMethod paymentMethod,
                                  String upiId, String linkedPhoneNumber, String password,
                                  String cardNumber, String cardHolderName, String expiryDate, String cvv) {

        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING)
                .upiId(upiId)
                .linkedPhoneNumber(linkedPhoneNumber)
                .password(password)
                .cardNumber(cardNumber)
                .cardHolderName(cardHolderName)
                .expiryDate(expiryDate)
                .cvv(cvv)
                .availableBalance(1000) // Example balance, this should be retrieved dynamically
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
                processCard(payment);
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
        boolean refundSuccess = simulateRefundTransaction();
        payment.setStatus(refundSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);

        return paymentRepository.save(payment);
    }

    private void processCOD(Payment payment) {
        payment.setStatus(PaymentStatus.COMPLETED);
        System.out.println("Payment completed via COD.");
    }

    private void processUPI(Payment payment) {
        boolean isValid = validateUPIPayment(payment);
        if (!isValid) {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println("UPI validation failed for " + payment.getPaymentMethod());
            return;
        }

        boolean transactionSuccess = simulateUPITransaction();
        payment.setStatus(transactionSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);

        if (transactionSuccess) {
            System.out.println("Payment completed via " + payment.getPaymentMethod() + ".");
        } else {
            System.out.println(payment.getPaymentMethod() + " payment failed.");
        }
    }

    private boolean validateUPIPayment(Payment payment) {
        switch (payment.getPaymentMethod()) {
            case UPI_GOOGLE_PAY:
            case UPI_PHONEPE:
            case UPI_PAYTM:
                return validateUPICommonFields(payment.getUpiId(), payment.getLinkedPhoneNumber(), payment.getPassword());
            default:
                throw new UnsupportedOperationException("Unsupported UPI method: " + payment.getPaymentMethod());
        }
    }

    private boolean validateUPICommonFields(String upiId, String linkedPhoneNumber, String password) {
        return validateUpiId(upiId) && validatePhoneNumber(linkedPhoneNumber) && validatePassword(password);
    }

    private boolean validatePhoneNumber(String linkedPhoneNumber) {
        String phoneRegex = "^[6-9]\\d{9}$";
        return linkedPhoneNumber != null && linkedPhoneNumber.matches(phoneRegex);
    }

    private boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    private boolean validateUpiId(String upiId) {
        String upiRegex = "^[\\w.-]+@[\\w.-]+$";
        return upiId != null && upiId.matches(upiRegex);
    }

    private void processCard(Payment payment) {
        if (!validateCardDetails(payment)) {
            payment.setStatus(PaymentStatus.FAILED);
            System.out.println("Card validation failed.");
            return;
        }

        boolean transactionSuccess = simulateCardTransaction(payment);
        payment.setStatus(transactionSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);

        if (transactionSuccess) {
            System.out.println("Payment completed via " + payment.getPaymentMethod() + ".");
        } else {
            System.out.println(payment.getPaymentMethod() + " payment failed.");
        }
    }

    private boolean validateCardDetails(Payment payment) {
        return validateCardNumber(payment.getCardNumber()) &&
                validateCVV(payment.getCvv()) &&
                validateExpiryDate(payment.getExpiryDate()) &&
                validateAvailableBalance(payment.getAvailableBalance(), payment.getAmount());
    }

    private boolean validateCardNumber(String cardNumber) {
        String cardRegex = "^[0-9]{16}$";
        return cardNumber != null && cardNumber.matches(cardRegex);
    }

    private boolean validateCVV(String cvv) {
        String cvvRegex = "^[0-9]{3}$";
        return cvv != null && cvv.matches(cvvRegex);
    }

    private boolean validateExpiryDate(String expiryDate) {
        String expiryRegex = "^(0[1-9]|1[0-2])/([0-9]{2})$";
        return expiryDate != null && expiryDate.matches(expiryRegex);
    }

    private boolean validateAvailableBalance(double availableBalance, double amount) {
        return availableBalance >= amount;
    }

    private boolean simulateUPITransaction() {
        return true; // Simulate success for testing purposes
    }

    private boolean simulateCardTransaction(Payment payment) {
        // Simulate deduction of amount from available balance
        payment.setAvailableBalance(payment.getAvailableBalance() - payment.getAmount());
        return payment.getAvailableBalance() >= 0; // Ensure balance is sufficient after deduction
    }

    private boolean simulateRefundTransaction() {
        return true; // Simulate success for testing purposes
    }
}
