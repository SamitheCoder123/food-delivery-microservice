package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.upi.PaymentUpi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentUpiRepository extends JpaRepository<PaymentUpi,Long> {
}
