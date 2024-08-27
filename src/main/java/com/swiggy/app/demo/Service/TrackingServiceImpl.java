package com.swiggy.app.demo.Service;

import com.swiggy.app.demo.Entity.Tracking;
import com.swiggy.app.demo.Repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private TrackingRepository trackingRepository;

    @Override
    public String addTrackingEntry(Tracking tracking) {
        LocalDateTime now = LocalDateTime.now();

        tracking.setTimestamp(convertToDateViaInstant(now));

        LocalDateTime expectedDeliveryTime = now.plusMinutes(45);
        tracking.setExpectedDeliveryTime(convertToDateViaInstant(expectedDeliveryTime));

        trackingRepository.save(tracking);
        return "successful";
    }

    @Override
    public Tracking getCurrentTracking(Long orderId) {
        List<Tracking> trackingHistory = trackingRepository.findByOrderId(orderId);

        if (trackingHistory.isEmpty()) {
            return null;
        }

        Tracking currentTracking = trackingHistory.get(trackingHistory.size() - 1);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime originalTimestamp = convertToLocalDateTimeViaInstant(currentTracking.getTimestamp());
        LocalDateTime expectedDeliveryTime = originalTimestamp.plusMinutes(46);

        long minutesRemaining = java.time.Duration.between(now, expectedDeliveryTime).toMinutes();
        String status;

        if (minutesRemaining > 30) {
            status = "Order Placed, Deliverd in(" + minutesRemaining + " min)";
        } else if (minutesRemaining > 10) {
            status = "Great news! Your order is being freshly crafted with the finest ingredients. Get ready to savor something delicious,it's almost on its way to you in(" + minutesRemaining + " min)";
        } else if (minutesRemaining > 3) {
            status = "Order is out for delivery, Agent is at your location. Share OTP and get your order in(" + minutesRemaining + " min)";
        } else {
            status = "Order delivered. Thank you!";
        }

        currentTracking.setStatus(status);

        currentTracking.setTimestamp(convertToDateViaInstant(now));
        currentTracking.setExpectedDeliveryTime(convertToDateViaInstant(expectedDeliveryTime));

        return currentTracking;
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        ZonedDateTime zonedDateTimeInIST = dateToConvert.atZone(ZoneId.of("Asia/Kolkata"));
        return Date.from(zonedDateTimeInIST.toInstant());
    }
}
