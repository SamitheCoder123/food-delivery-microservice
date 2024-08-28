package com.swiggy.app.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    // New fields for UPI validation
    @Column(nullable = true)
    private String upiId;

    @Column(nullable = true)
    private String linkedPhoneNumber;

    @Column(nullable = true)
    private String password;

    // New fields for card validation
    @Column(nullable = true)
    private String cardNumber;

    @Column(nullable = true)
    private String cardHolderName;

    @Column(nullable = true)
    private String expiryDate;

    @Column(nullable = true)
    private String cvv;

    @Column(nullable = true)
    private double availableBalance;
}
