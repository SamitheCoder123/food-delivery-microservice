package com.swiggy.app.demo.controller;

import com.swiggy.app.demo.entity.Payment;
import com.swiggy.app.demo.repository.PaymentRepository;
import com.swiggy.app.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author
 **/
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody Payment paymentRequest) {
        Payment payment = paymentService.processPayment(
                paymentRequest.getOrderId(),
                paymentRequest.getAmount(),
                paymentRequest.getPaymentMethod()
        );
        paymentRepository.save(payment);

        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(payment.get(), HttpStatus.OK);
    }

    /*@PutMapping("/{id}/refund")
    public ResponseEntity<RefundDTO> refundPayment(@PathVariable Long id, @RequestBody RefundDTO refundDTO) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        if (paymentOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Refund refund = paymentService.processRefund(paymentOptional.get(), refundDTO);
        refundRepository.save(refund);

        RefundDTO responseDTO = new RefundDTO(refund.getAmount(), refund.getReason(), refund.getRefundMethod(), refund.getRefundAccount());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }*/

}