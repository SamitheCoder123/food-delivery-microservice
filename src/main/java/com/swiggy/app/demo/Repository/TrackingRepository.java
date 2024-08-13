package com.swiggy.app.demo.Repository;

import com.swiggy.app.demo.Entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByOrderId(Long orderId);
}
