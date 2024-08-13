package com.swiggy.app.demo.Service;

import com.swiggy.app.demo.Entity.Tracking;
import com.swiggy.app.demo.Repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private TrackingRepository trackingRepository;

    @Override
    public void addTrackingEntry(Tracking tracking) {
        tracking.setTimestamp(LocalDateTime.now());
        trackingRepository.save(tracking);
    }

    @Override
    public List<Tracking> getTrackingHistory(Long orderId) {
        return trackingRepository.findByOrderId(orderId);
    }
}
