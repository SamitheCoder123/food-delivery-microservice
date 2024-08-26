package com.swiggy.app.demo.Controller;

import com.swiggy.app.demo.Entity.Tracking;
import com.swiggy.app.demo.Service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @PostMapping("/orders/{orderId}")
    public ResponseEntity<String> addTrackingEntry(@PathVariable Long orderId, @RequestBody Tracking tracking) {
        tracking.setOrderId(orderId);
        String success = trackingService.addTrackingEntry(tracking);
        return new ResponseEntity<>(success,HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Tracking> getTrackingStatus(@PathVariable Long orderId) {
        Tracking tracking = trackingService.getCurrentTracking(orderId);
        return new ResponseEntity<>(tracking, HttpStatus.OK);
    }
}
