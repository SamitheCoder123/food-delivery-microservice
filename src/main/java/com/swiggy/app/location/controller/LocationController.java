package com.swiggy.app.location.controller;

import com.swiggy.app.location.entity.Location;
import com.swiggy.app.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

/**
 * @author
 **/
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Optional<Location> getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @PostMapping
    public Location addLocation(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }

    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable Long id, @RequestBody Location location) {
        location.setId(id);
        return locationService.saveLocation(location);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }

    @GetMapping("/load")
    public ResponseEntity<String> loadLocationsToDatabase() {
        try {
            locationService.loadLocations();
            return ResponseEntity.ok("Locations loaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to load locations: " + e.getMessage());
        }
    }

    @PostMapping("/getResto")
    public ResponseEntity<String> getRestaurant(@RequestBody Map<String,Object> request){
        String restaurantByLocation = locationService.getRestaurantByLocation(request);
        return ResponseEntity.ok(restaurantByLocation);
    }
}

