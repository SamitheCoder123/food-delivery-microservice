package com.swiggy.app.location.service;

import com.swiggy.app.location.entity.Location;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author
 **/
public interface LocationService {
    List<Location> getAllLocations();

    Optional<Location> getLocationById(Long id);

    Location saveLocation(Location location);

    void deleteLocation(Long id);

    void loadLocations();

    String getRestaurantByLocation(Map<String, Object> request);
}
