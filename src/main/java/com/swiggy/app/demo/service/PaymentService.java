package com.swiggy.app.demo.service;

import com.swiggy.app.demo.dto.CashOnDeliveryDTO;
import com.swiggy.app.demo.dto.PaymentCardDto;
import com.swiggy.app.demo.dto.PaymentUpiDto;
import com.swiggy.app.demo.entity.cards.PaymentCard;
import com.swiggy.app.demo.entity.PaymentMethod;

import java.util.Optional;

public interface PaymentService {
    PaymentCardDto processPayment(String orderId, double amount, PaymentMethod paymentMethod, String cardNumber, String cardHolderName, String expiryDate, String cvv, double availableBalance);

    PaymentUpiDto processPaymentUpi(String orderId, double amount, PaymentMethod paymentMethod, String linkedPhoneNumber, String upiId, String password);

    CashOnDeliveryDTO processPaymentCod(Long orderId, Double amount);

    Optional<PaymentCard> getPaymentById(Long id);

    PaymentCard refundPayment(Long paymentId);

}
