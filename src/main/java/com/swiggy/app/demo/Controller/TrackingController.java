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
    public ResponseEntity<Void> addTrackingEntry(@PathVariable Long orderId, @RequestBody Tracking tracking) {
        trackingService.addTrackingEntry(tracking);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<List<Tracking>> getTrackingHistory(@PathVariable Long orderId) {
        List<Tracking> trackingHistory = trackingService.getTrackingHistory(orderId);
        return new ResponseEntity<>(trackingHistory, HttpStatus.OK);
    }
}
