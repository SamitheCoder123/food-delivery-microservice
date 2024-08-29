package com.swiggy.app.demo.entity.cards;

import com.swiggy.app.demo.entity.PaymentMethod;
import com.swiggy.app.demo.entity.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCard {

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
