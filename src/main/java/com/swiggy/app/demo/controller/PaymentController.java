package com.swiggy.app.demo.controller;

import com.swiggy.app.demo.dto.CashOnDeliveryDTO;
import com.swiggy.app.demo.dto.PaymentCardDto;
import com.swiggy.app.demo.dto.PaymentUpiDto;
import com.swiggy.app.demo.entity.cards.PaymentCard;
import com.swiggy.app.demo.entity.cod.CashOnDelivery;
import com.swiggy.app.demo.entity.upi.PaymentUpi;
import com.swiggy.app.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/cards")
    public ResponseEntity<PaymentCardDto> processPayment(@RequestBody PaymentCard paymentRequestCard) {
        try {
            PaymentCardDto paymentCardDto = paymentService.processPayment(
                    paymentRequestCard.getOrderId(),
                    paymentRequestCard.getAmount(),
                    paymentRequestCard.getPaymentMethod(),
                    paymentRequestCard.getCardNumber(),
                    paymentRequestCard.getCardHolderName(),
                    paymentRequestCard.getExpiryDate(),
                    paymentRequestCard.getCvv(),
                    paymentRequestCard.getAvailableBalance()
            );
            return new ResponseEntity<>(paymentCardDto, HttpStatus.CREATED);
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upi")
    public ResponseEntity<PaymentUpiDto> processPaymentUpi(@RequestBody PaymentUpi paymentRequestUpi) {
        try {
            PaymentUpiDto paymentUpiDto = paymentService.processPaymentUpi(
                    paymentRequestUpi.getOrderId(),
                    paymentRequestUpi.getAmount(),
                    paymentRequestUpi.getPaymentMethod(),
                    paymentRequestUpi.getLinkedPhoneNumber(),
                    paymentRequestUpi.getUpiId(),
                    paymentRequestUpi.getPassword()
            );
            return new ResponseEntity<>(paymentUpiDto, HttpStatus.CREATED);
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cod")
    public ResponseEntity<CashOnDeliveryDTO> processPaymentCod(@RequestBody CashOnDelivery paymentRequestCod) {
        try {
            CashOnDeliveryDTO cashOnDeliveryDTO = paymentService.processPaymentCod(
                    paymentRequestCod.getOrderId(),
                    paymentRequestCod.getAmount()
            );
            return new ResponseEntity<>(cashOnDeliveryDTO, HttpStatus.CREATED);
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentCard> getPayment(@PathVariable Long id) {
        Optional<PaymentCard> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Refund Payment
    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentCard> refundPayment(@PathVariable Long id) {
        try {
            PaymentCard refundedPayment = paymentService.refundPayment(id);
            return new ResponseEntity<>(refundedPayment, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
