package com.swiggy.app.demo.service;

import com.swiggy.app.demo.entity.Location;

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

    void loadLocations();
}
