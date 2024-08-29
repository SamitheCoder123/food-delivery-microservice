package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.cod.CashOnDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCodRepository extends JpaRepository<CashOnDelivery,Long> {
}
