package com.swiggy.app.demo.dto;

import com.swiggy.app.demo.entity.PaymentMethod;
import com.swiggy.app.demo.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentUpiDto {
    private Long id;
    private String orderId;
    private double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String upiId;
    private String linkedPhoneNumber;
    private String password;
}
