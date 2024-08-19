package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *@author
 **/

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Optional<Payment> findByUsername(String username);
    Optional<Payment> findByEmail(String email);
}
