package com.swiggy.app.demo.Service;

import com.swiggy.app.demo.Entity.Tracking;

import java.util.List;

public interface TrackingService {
    String addTrackingEntry(Tracking tracking);
    Tracking getCurrentTracking(Long orderId);
}
