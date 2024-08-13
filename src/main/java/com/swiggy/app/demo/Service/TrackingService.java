package com.swiggy.app.demo.Service;

import com.swiggy.app.demo.Entity.Tracking;

import java.util.List;

public interface TrackingService {
    void addTrackingEntry(Tracking tracking);
    List<Tracking> getTrackingHistory(Long orderId);
}
