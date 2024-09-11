package com.swiggy.app.location.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swiggy.app.location.entity.Location;
import com.swiggy.app.location.entity.restaurant.Restaurant;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author
 **/
public interface LocationService {
    List<Location> getAllLocations();

    Optional<Location> getLocationById(Long id);

    Location saveLocation(Location location);

    void deleteLocation(Long id);

    ResponseEntity<String> loadLocations() throws JsonProcessingException;

    ResponseEntity<String> findByLocationId(Long id);

    Location findById(Long id);

    Restaurant getRestaurantByLocation(Long id);
}
