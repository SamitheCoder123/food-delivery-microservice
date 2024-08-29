package com.swiggy.app.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashOnDeliveryDTO {
    private Long id;
    private Long orderId;
    private Double amount;
    private String status;
}