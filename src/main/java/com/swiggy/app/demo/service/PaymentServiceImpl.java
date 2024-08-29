package com.swiggy.app.demo.service;

import com.swiggy.app.demo.dto.CashOnDeliveryDTO;
import com.swiggy.app.demo.dto.PaymentCardDto;
import com.swiggy.app.demo.dto.PaymentUpiDto;
import com.swiggy.app.demo.entity.cards.PaymentCard;
import com.swiggy.app.demo.entity.PaymentMethod;
import com.swiggy.app.demo.entity.PaymentStatus;
import com.swiggy.app.demo.entity.cod.CashOnDelivery;
import com.swiggy.app.demo.entity.upi.PaymentUpi;
import com.swiggy.app.demo.repository.PaymentCodRepository;
import com.swiggy.app.demo.repository.PaymentRepository;
import com.swiggy.app.demo.repository.PaymentUpiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentUpiRepository paymentUpiRepository;

    @Autowired
    private PaymentCodRepository paymentCodRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PaymentServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentCardDto processPayment(String orderId, double amount, PaymentMethod paymentMethod,
                                         String cardNumber, String cardHolderName, String expiryDate, String cvv, double availableBalance) {

        PaymentCard paymentcard = PaymentCard.builder()
                .orderId(orderId)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING)
                .cardNumber(cardNumber)
                .cardHolderName(cardHolderName)
                .expiryDate(expiryDate)
                .cvv(cvv)
                .availableBalance(availableBalance)
                .build();

        PaymentCard paymentcardResponse = processCard(paymentcard);

        PaymentCard payment_done = paymentRepository.save(paymentcardResponse);
        return modelMapper.map(payment_done, PaymentCardDto.class);
    }

    @Override
    public Optional<PaymentCard> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public PaymentCard refundPayment(Long paymentId) {
        Optional<PaymentCard> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with ID: " + paymentId);
        }

        PaymentCard payment = paymentOpt.get();
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot refund payment with status: " + payment.getStatus());
        }

        payment.setStatus(PaymentStatus.PENDING);
        boolean refundSuccess = simulateRefundTransaction();
        payment.setStatus(refundSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);

        return paymentRepository.save(payment);
    }

    @Override
    public PaymentUpiDto processPaymentUpi(String orderId, double amount, PaymentMethod paymentMethod, String linkedPhoneNumber, String upiId, String password) {
        PaymentUpi paymentUpi = PaymentUpi.builder()
                .orderId(orderId)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING)
                .linkedPhoneNumber(linkedPhoneNumber)
                .upiId(upiId)
                .password(password)
                .build();

        PaymentUpi paymentUpiResponse = processUPI(paymentUpi);

        PaymentUpi payment_done = paymentUpiRepository.save(paymentUpiResponse);
        return modelMapper.map(payment_done, PaymentUpiDto.class);
    }

    @Override
    public CashOnDeliveryDTO processPaymentCod(Long orderId, Double amount) {
        CashOnDelivery cashOnDelivery = CashOnDelivery.builder()
                .orderId(orderId)
                .amount(amount)
                .build();

        CashOnDelivery paymentCodResponse = processCOD(cashOnDelivery);

        CashOnDelivery payment_done = paymentCodRepository.save(paymentCodResponse);
        return modelMapper.map(payment_done, CashOnDeliveryDTO.class);
    }

    private CashOnDelivery processCOD(CashOnDelivery paymentcod) {
        paymentcod.setStatus(PaymentStatus.COMPLETED);
        System.out.println("Payment completed via COD.");
        return paymentcod;
    }

    private PaymentUpi processUPI(PaymentUpi paymentUpi) {
        boolean isValid = validateUPIPayment(paymentUpi);
        if (!isValid) {
            paymentUpi.setStatus(PaymentStatus.FAILED);
            System.out.println("UPI validation failed for " + paymentUpi.getPaymentMethod());
        }

        boolean transactionSuccess = simulateUPITransaction();
        paymentUpi.setStatus(transactionSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);

        if (transactionSuccess) {
            System.out.println("Payment completed via " + paymentUpi.getPaymentMethod() + ".");
        } else {
            System.out.println(paymentUpi.getPaymentMethod() + " payment failed.");
        }
        return paymentUpi;
    }

    private boolean validateUPIPayment(PaymentUpi payment) {
         return validateUPICommonFields(payment.getUpiId(), payment.getLinkedPhoneNumber(), payment.getPassword());

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

    private PaymentCard processCard(PaymentCard paymentCard) {
        if (!validateCardDetails(paymentCard)) {
            paymentCard.setStatus(PaymentStatus.FAILED);
            System.out.println("Card validation failed.");
        }

        boolean transactionSuccess = simulateCardTransaction(paymentCard);
        paymentCard.setStatus(transactionSuccess ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);

        if (transactionSuccess) {
            System.out.println("Payment completed via " + paymentCard.getPaymentMethod() + ".");
        } else {
            System.out.println(paymentCard.getPaymentMethod() + " payment failed.");
        }
        return paymentCard;
    }

    private boolean validateCardDetails(PaymentCard payment) {
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
        return true;
    }

    private boolean simulateCardTransaction(PaymentCard payment) {
        payment.setAvailableBalance(payment.getAvailableBalance() - payment.getAmount());
        return payment.getAvailableBalance() >= 0;
    }

    private boolean simulateRefundTransaction() {
        return true;
    }
}
