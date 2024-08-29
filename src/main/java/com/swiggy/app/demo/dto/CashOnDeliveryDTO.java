package com.swiggy.app.demo.dto;

import com.swiggy.app.demo.entity.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashOnDeliveryDTO {
    private Long id;
    private Long orderId;
    private Double amount;
    private PaymentMethod paymentMethod;
    private String status;
}