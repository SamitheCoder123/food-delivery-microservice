package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.cards.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentCard, Long> {
    // Additional query methods can be added here if needed
}
