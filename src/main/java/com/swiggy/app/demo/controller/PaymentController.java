package com.swiggy.app.demo.controller;

import com.swiggy.app.demo.entity.Payment;
import com.swiggy.app.demo.entity.PaymentMethod;
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

    // Process Payment
    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody Payment paymentRequest) {
        try {
            Payment payment = paymentService.processPayment(
                    paymentRequest.getOrderId(),
                    paymentRequest.getAmount(),
                    paymentRequest.getPaymentMethod()
            );
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Refund Payment
    @PostMapping("/{id}/refund")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long id) {
        try {
            Payment refundedPayment = paymentService.refundPayment(id);
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
